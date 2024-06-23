package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.domain.Book;
import dongduk.cs.moaread.domain.Category;
import dongduk.cs.moaread.domain.Post;
import dongduk.cs.moaread.dto.post.request.PostReqDto;
import dongduk.cs.moaread.service.CategoryService;
import dongduk.cs.moaread.service.BookService;
import dongduk.cs.moaread.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;
    private final BookService bookSearchService;

    /* 서평 등록 Form */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{bookIsbn}")
    public String createPost(@PathVariable String bookIsbn, Model model, Principal principal) {
        Book book = bookService.getBookDetail(bookIsbn);
        List<Category> categoryList = categoryService.getCategoryList("/blog/" + principal.getName());
        PostReqDto postReqDto = new PostReqDto();
        postReqDto.setBookIsbn(bookIsbn);

        model.addAttribute("book", book);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("postDto", postReqDto);

        return "create_post_form";
    }

    /* 서평 등록 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("postDto") PostReqDto postReqDto,
                             BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            Book book = bookService.getBookDetail(postReqDto.getBookIsbn());
            List<Category> categoryList = categoryService.getCategoryList("/blog/" + principal.getName());

            model.addAttribute("book", book);
            model.addAttribute("categoryList", categoryList);

            return "create_post_form";
        }

        String blogUrl = "/blog/" + principal.getName() + "/1";

        postService.createPost(postReqDto);

        return "redirect:" + blogUrl;
    }

    /* 서평 수정 Form */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{id}")
    public String updatePost(@PathVariable Long id, Model model, Principal principal) {
        Post post = postService.getPost(id);
        Book book = bookService.getBookDetail(post.getBookIsbn());
        Category category = categoryService.getCategory(post.getCategoryId());
        List<Category> categoryList = categoryService.getCategoryList(principal.getName());

        if (!("/blog/" + principal.getName()).equals(category.getBlogUrl())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        PostReqDto postReqDto = new PostReqDto();
        postReqDto.setTitle(post.getTitle());
        postReqDto.setContent(post.getContent());
        postReqDto.setCategoryId(post.getCategoryId());
        postReqDto.setBookIsbn(post.getBookIsbn());

        model.addAttribute("book", book);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("postDto", postReqDto);
        model.addAttribute("postId", id);

        return "update_post_form";
    }

    /* 서평 수정 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String updatePost(@RequestParam Long id, @Valid @ModelAttribute("postDto") PostReqDto postReqDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update_post_form";
        }

        postService.updatePost(id, postReqDto);

        return "redirect:/post/detail/" + id;
    }

    /* 서평 삭제 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, Model model, Principal principal) {
        Post post = postService.getPost(id);
        Category category = categoryService.getCategory(post.getCategoryId());

        if (!("/blog/" + principal.getName()).equals(category.getBlogUrl())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        postService.deletePost(id);

        return "redirect:/post/detail/" + id;
    }

    /* 서평 상세 조회 */
    @GetMapping("/detail/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) {
        Post post = postService.getPost(id);
        Book book = bookService.getBookDetail(post.getBookIsbn());
        Category category = categoryService.getCategory(post.getCategoryId());

        boolean isOwner = false;

        if (principal != null) {
            isOwner = ("/blog/" + principal.getName()).equals(category.getBlogUrl());
        }

        // 조회수 증가
        if (!isOwner) {
            postService.increaseViews(id);
        }

        model.addAttribute("post", post);
        model.addAttribute("book", book);
        model.addAttribute("category", category);
        model.addAttribute("isOwner", isOwner);

        return "post";
    }
}
