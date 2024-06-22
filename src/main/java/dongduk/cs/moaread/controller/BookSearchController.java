package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.domain.Book;
import dongduk.cs.moaread.service.NaverBookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final NaverBookSearchService naverBookSearchService;

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

    @GetMapping("/search/detail")
    public String getBookDetail(@RequestParam String isbn, @RequestParam String query, Model model) {
        Book book = naverBookSearchService.getBookDetail(isbn);
        model.addAttribute("book", book);
        model.addAttribute("query", query);
        return "book_detail";
    }
}
