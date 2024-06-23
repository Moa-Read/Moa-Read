package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.domain.Book;
import dongduk.cs.moaread.service.LikesService;
import dongduk.cs.moaread.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final LikesService likesService;

    @GetMapping("/search")
    public String searchBooks(@RequestParam String query,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "sim") String sort,
                              Model model,
                              Principal principal) {
        int pageSize = 10;
        List<Book> books = bookService.searchBooks(query, sort);
        bookService.saveBooks(books);

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
        if (principal != null) {
            model.addAttribute("userId", principal.getName());
        }
        return "search";
    }

    @GetMapping("/search/detail")
    public String getBookDetail(@RequestParam String isbn, @RequestParam String query, Model model, Principal principal) {
        Book book = bookService.getBookDetail(isbn);
        model.addAttribute("book", book);
        model.addAttribute("query", query);
        if (principal != null) {
            String userId = principal.getName();
            boolean liked = likesService.isBookLikedByUser(userId, isbn);
            model.addAttribute("liked", liked);
            model.addAttribute("userId", userId);
        } else {
            model.addAttribute("liked", false);
        }
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/unlike")
    public String unlikeBook(@RequestParam String bookIsbn, @RequestParam String query, Principal principal) {
        if (principal == null) {
            return "redirect:/user/login";
        }
        String userId = principal.getName();  // 인증된 사용자 이름 가져오기
        likesService.unlikeBook(userId, bookIsbn);
        return "redirect:/search/detail?isbn=" + bookIsbn + "&query=" + query;
    }

    @GetMapping("/")
    public String getMainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Book> books;
        if (userDetails != null) {
            String userId = userDetails.getUsername();
            books = bookService.getBooksLikedBySimilarUsers(userId);
        } else {
            books = bookService.getTopLikedBooks(5);
        }
        model.addAttribute("todayBooks", books);
        return "main";
    }
}
