package dongduk.cs.moaread.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dongduk.cs.moaread.service.NaverBookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.logging.Logger;

@Controller
public class BookSearchController {

    private static final Logger LOGGER = Logger.getLogger(BookSearchController.class.getName());

    @Autowired
    private NaverBookSearchService naverBookSearchService;

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
}
