package dongduk.cs.moaread.controller;

<<<<<<< Updated upstream
import dongduk.cs.moaread.domain.Book;
import dongduk.cs.moaread.service.NaverBookSearchService;
import lombok.RequiredArgsConstructor;
=======
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
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< Updated upstream
import java.util.List;
=======
<<<<<<< Updated upstream
import java.util.Collections;
import java.util.logging.Logger;
=======
import java.security.Principal;
import java.util.List;
>>>>>>> Stashed changes
>>>>>>> Stashed changes

@Controller
@RequiredArgsConstructor
public class BookSearchController {

<<<<<<< Updated upstream
    private final NaverBookSearchService naverBookSearchService;
=======
<<<<<<< Updated upstream
    private static final Logger LOGGER = Logger.getLogger(BookSearchController.class.getName());

    @Autowired
    private NaverBookSearchService naverBookSearchService;
=======
    private final NaverBookSearchService naverBookSearchService;
    private final LikesService likesService;
>>>>>>> Stashed changes
>>>>>>> Stashed changes

    @GetMapping("/search")
    public String searchBooks(@RequestParam String query, @RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        List<Book> books = naverBookSearchService.searchBooks(query);
        naverBookSearchService.saveBooks(books);

        int totalBooks = books.size();
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalBooks);

        List<Book> paginatedBooks = books.subList(fromIndex, toIndex);

        model.addAttribute("books", paginatedBooks);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("query", query);
        return "search";
    }
<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream
=======
>>>>>>> Stashed changes

    @GetMapping("/search/detail")
    public String getBookDetail(@RequestParam String isbn, @RequestParam String query, Model model) {
        Book book = naverBookSearchService.getBookDetail(isbn);
        model.addAttribute("book", book);
        model.addAttribute("query", query);
        return "book_detail";
    }
<<<<<<< Updated upstream
=======

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
>>>>>>> Stashed changes
}
