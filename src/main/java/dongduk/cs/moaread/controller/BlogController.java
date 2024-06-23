package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.domain.Blog;
import dongduk.cs.moaread.dto.blog.request.BlogUpdateReqDto;
import dongduk.cs.moaread.dto.blog.response.BlogResDto;
import dongduk.cs.moaread.service.BlogService;
import dongduk.cs.moaread.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @GetMapping("/{userId}/{categoryId}")
    public String getBlog(@PathVariable String userId, @PathVariable Long categoryId,
                          @RequestParam(value = "sort", defaultValue = "CREATED_AT") String sort,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                          Model model, Principal principal) {
        BlogResDto blogResDto = blogService.getBlog(userId, categoryId, sort, pageNum, pageSize);
        int totalSize = blogResDto.getTotalSize();
        int totalPage = (totalSize % 10 > 0 ? ((totalSize / 10) + 1) : totalSize);

        boolean isLoggedIn = (principal != null);
        boolean isOwner = false;
        boolean isSubscribed = false;

        if (isLoggedIn) {
            isOwner = blogResDto.getBlog().getUserId().equals(principal.getName());
            isSubscribed = blogService.isSubscribed(principal.getName(), blogResDto.getBlog().getUrl());
        }

        model.addAttribute("blogResDto", blogResDto);
        model.addAttribute(("categoryId"), categoryId);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("isSubscribed", isSubscribed);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPage", totalPage);

        return "blog";
    }

    /* 블로그 수정 Form */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update")
    public String updateBlog(Model model, Principal principal) {
        Blog blog = blogService.getBlogInfo(principal.getName());

        model.addAttribute("updateBlogDto", blog);

        return "update_blog_form";
    }

    /* 블로그 수정 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public String updateBlog(@Valid @ModelAttribute("updateBlogDto")BlogUpdateReqDto blogUpdateReqDto,
                             BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "update_blog_form";
        }

        String url = "/blog/" + principal.getName();

        blogService.updateBlog(url, blogUpdateReqDto);

        return "redirect:" + url;
    }

    /* 블로그 구독 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/subscribe/{blogId}")
    public String subscribe(@PathVariable String blogId, Principal principal) {
        String userId = principal.getName();
        String blogUrl = "/blog/" + blogId;

        blogService.subscribe(userId, blogUrl);

        return "redirect:" + blogUrl;
    }

    /* 블로그 구독 취소 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/unsubscribe/{blogId}")
    public String unsubscribe(@PathVariable String blogId, Principal principal) {
        String userId = principal.getName();
        String blogUrl = "/blog/" + blogId;

        blogService.unsubscribe(userId, blogUrl);

        return "redirect:" + blogUrl;
    }
}
