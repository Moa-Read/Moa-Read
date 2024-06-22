package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.domain.Blog;
import dongduk.cs.moaread.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;

    /* 블로그 전체 조회 */
    @GetMapping()
    public String getBlogList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             Model model) {
        List<Blog> blogList = blogService.getAllBlogList(pageNum, pageSize);
        int totalSize = blogService.getAllBlogCount();
        int totalPage = (totalSize % 10 > 0 ? ((totalSize / 10) + 1) : totalSize);

        model.addAttribute("blogList", blogList);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPage", totalPage);

        return "blog_list";
    }

    /* 블로그 상세 조회 */
    @GetMapping("/{userId}")
    public String getBlog(@PathVariable String userId, Model model) {
        Blog blog = blogService.getBlog(userId);

        model.addAttribute("blog", blog);

        return "blog";
    }
}
