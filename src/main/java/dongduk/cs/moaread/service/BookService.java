package dongduk.cs.moaread.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dongduk.cs.moaread.dao.BookDao;
import dongduk.cs.moaread.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private final BookDao bookDao;
    private final RestTemplate restTemplate;

    public List<Book> searchBooks(String query, String sort) {
        List<Book> books = new ArrayList<>();
        String apiURL = "https://openapi.naver.com/v1/search/book.json";
        query = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
        int display = 100;  // 최대 100개 결과 요청

        String url = apiURL + "?query=" + query + "&display=" + display + "&sort=" + sort;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode items = objectMapper.readTree(result).get("items");
            if (items != null) {
                for (JsonNode item : items) {
                    String pubDate = item.path("pubdate").asText();
                    if (pubDate == null || pubDate.isEmpty()) {
                        continue;
                    }

                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date publishDate;
                    try {
                        publishDate = inputFormat.parse(pubDate);
                    } catch (ParseException e) {
                        throw new RuntimeException("잘못된 날짜 형식입니다: " + pubDate, e);
                    }

                    String isbn = item.path("isbn").asText();
                    Book book = new Book();
                    book.setIsbn(isbn);
                    book.setTitle(truncateString(item.path("title").asText(), 100));
                    book.setAuthor(truncateString(item.path("author").asText(), 50));
                    book.setPublisher(truncateString(item.path("publisher").asText(), 50));
                    book.setDescription(truncateString(item.path("description").asText(), 500));
                    book.setPrice(item.path("discount").asInt(0));
                    book.setPublishDate(java.sql.Date.valueOf(outputFormat.format(publishDate)));

                    String imageUrl = item.path("image").asText();
                    book.setImage(imageUrl);

                    books.add(book);
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("책 검색 중 오류가 발생했습니다.", e);
        }
        return books;
    }

    @Transactional
    public void saveBooks(List<Book> books) {
        for (Book book : books) {
            Book existingBook = bookDao.findByIsbn(book.getIsbn());
            if (existingBook == null) {
                bookDao.insertBook(book);
            }
        }
    }

    @Transactional(readOnly = true)
    public Book getBookDetail(String isbn) {
        return bookDao.findByIsbn(isbn);
    }

    private String truncateString(String value, int length) {
        if (value != null && value.length() > length) {
            return value.substring(0, length);
        }
        return value;
    }

    @Transactional(readOnly = true)
    public List<Book> getTopLikedBooks(int limit) {
        return bookDao.findTopLikedBooks(limit);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksLikedByUser(String userId) {
        return bookDao.findBooksLikedByUser(userId);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksLikedBySimilarUsers(String userId) {
        return bookDao.findBooksLikedBySimilarUsers(userId);
    }
}
