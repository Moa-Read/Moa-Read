package dongduk.cs.moaread.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dongduk.cs.moaread.dao.BookDao;
import dongduk.cs.moaread.domain.Book;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class NaverBookSearchService {

    private static final Logger LOGGER = Logger.getLogger(NaverBookSearchService.class.getName());

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private final BookDao bookDao;
    private final RestTemplate restTemplate;

    @Transactional
    public String searchBooks(String query, int display, int start, String sort) {
        String apiURL = "https://openapi.naver.com/v1/search/book.json";
        try {
            query = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8.toString());
            LOGGER.info("Encoded query: " + query);
            String url = apiURL + "?query=" + query + "&display=" + display + "&start=" + start + "&sort=" + sort;
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", clientId);
            headers.set("X-Naver-Client-Secret", clientSecret);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // 응답 상태 코드와 응답 본문을 로그로 남기기
            LOGGER.info("Response Status Code: " + response.getStatusCode());
            LOGGER.info("Response Body: " + response.getBody());

            return response.getBody();
        } catch (Exception e) {
            LOGGER.severe("Exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void saveBooks(String result) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(result);
            JsonNode items = root.path("items");

            for (JsonNode item : items) {
                String isbn = item.path("isbn").asText();
                if (bookDao.findByIsbn(isbn) == null) {
                    Book book = new Book();
                    book.setIsbn(isbn);
                    book.setTitle(truncateString(item.path("title").asText(), 100));
                    book.setAuthor(truncateString(item.path("author").asText(), 50));
                    book.setTranslator(truncateString(item.path("translator").asText(null), 50));
                    book.setPublisher(truncateString(item.path("publisher").asText(), 50));

                    // NULL 체크를 확실히 해서 DESCRIPTION에 빈 문자열 삽입
                    String description = item.hasNonNull("description") ? item.path("description").asText() : "";
                    if (description == null || description.isEmpty()) {
                        description = "";
                    }
                    book.setDescription(truncateString(description, 500));

                    book.setPrice(item.path("discount").asInt(0));

                    // Parsing the publish date
                    String pubDate = item.path("pubdate").asText();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    Date publishDate = formatter.parse(pubDate);
                    book.setPublishDate(new java.sql.Date(publishDate.getTime()));

                    // Download and set the image
                    String imageUrl = item.path("image").asText();
                    byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
                    book.setImage(imageBytes);

                    // Log the book object for debugging
                    LOGGER.info("Inserting book: " + book);

                    bookDao.insertBook(book);
                }
            }
        } catch (MyBatisSystemException e) {
            LOGGER.severe("MyBatisSystemException: " + e.getMessage());
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                LOGGER.severe("Cause: " + cause.getMessage());
                cause.printStackTrace();
            }
        } catch (Exception e) {
            LOGGER.severe("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String truncateString(String value, int length) {
        if (value != null && value.length() > length) {
            return value.substring(0, length);
        }
        return value;
    }
}
