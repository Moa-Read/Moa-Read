package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.domain.Book;
import dongduk.cs.moaread.service.LikesService;
import dongduk.cs.moaread.service.NaverBookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final NaverBookSearchService naverBookSearchService;
    private final LikesService likesService;

    @GetMapping("/search")
    public String searchBooks(@RequestParam String query,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "sim") String sort,
                              Model model) {
        int pageSize = 10;
        List<Book> books = naverBookSearchService.searchBooks(query, sort);
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
        model.addAttribute("sort", sort);
        return "search";
    }

    @GetMapping("/search/detail")
    public String getBookDetail(@RequestParam String isbn, @RequestParam String query, Model model) {
        Book book = naverBookSearchService.getBookDetail(isbn);
        model.addAttribute("book", book);
        model.addAttribute("query", query);
        return "book_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/like")
    public String likeBook(@RequestParam String bookIsbn, @RequestParam String query, Principal principal) {
        if (principal == null) {
            return "redirect:/user/login";
        }
        String userId = principal.getName();  // 인증된 사용자 이름 가져오기
        likesService.likeBook(userId, bookIsbn);
        return "redirect:/search/detail?isbn=" + bookIsbn + "&query=" + query;
    }
}
