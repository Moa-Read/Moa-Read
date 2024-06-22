package dongduk.cs.moaread.controller;

<<<<<<< Updated upstream
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dongduk.cs.moaread.service.NaverBookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
=======
import dongduk.cs.moaread.domain.Book;
import dongduk.cs.moaread.service.LikesService;
import dongduk.cs.moaread.service.NaverBookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< Updated upstream
import java.util.Collections;
import java.util.logging.Logger;
=======
import java.security.Principal;
import java.util.List;
>>>>>>> Stashed changes

@Controller
public class BookSearchController {

<<<<<<< Updated upstream
    private static final Logger LOGGER = Logger.getLogger(BookSearchController.class.getName());

    @Autowired
    private NaverBookSearchService naverBookSearchService;
=======
    private final NaverBookSearchService naverBookSearchService;
    private final LikesService likesService;
>>>>>>> Stashed changes

    @GetMapping("/search")
    public String searchBooks(@RequestParam String query,
                              @RequestParam(defaultValue = "10") int display,
                              @RequestParam(defaultValue = "1") int start,
                              @RequestParam(defaultValue = "sim") String sort,
                              Model model) {
        try {
            String result = naverBookSearchService.searchBooks(query, display, start, sort);

            // 저장 메서드 호출
            naverBookSearchService.saveBooks(result);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode items = objectMapper.readTree(result).path("items");

            model.addAttribute("items", items.isMissingNode() ? Collections.emptyList() : items);
            LOGGER.info("Items added to model: " + items);
        } catch (Exception e) {
            LOGGER.severe("Exception during JSON parsing: " + e.getMessage());
            model.addAttribute("items", Collections.emptyList());
        }

        return "search";
    }
<<<<<<< Updated upstream
=======

    @GetMapping("/search/detail")
    public String getBookDetail(@RequestParam String isbn, @RequestParam String query, Model model) {
        Book book = naverBookSearchService.getBookDetail(isbn);
        model.addAttribute("book", book);
        model.addAttribute("query", query);
        return "book_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/like")
    public String likeBook(@RequestParam String bookIsbn, Principal principal) {
        if (principal == null) {
            return "redirect:/user/login";
        }
        String userId = principal.getName();  // 인증된 사용자 이름 가져오기
        likesService.likeBook(userId, bookIsbn);
        return "redirect:/search/detail?isbn=" + bookIsbn;
    }
>>>>>>> Stashed changes
}
