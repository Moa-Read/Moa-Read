package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.domain.Category;
import dongduk.cs.moaread.dto.category.request.CategoryReqDto;
import dongduk.cs.moaread.exception.DuplicatedCategoryNameException;
import dongduk.cs.moaread.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    /* 카테고리 생성 Form */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createCategory(Model model) {
        CategoryReqDto categoryReqDto = new CategoryReqDto();

        model.addAttribute("categoryDto", categoryReqDto);

        return "create_category_form";
    }

    /* 카테고리 생성 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("categoryDto") CategoryReqDto categoryReqDto,
                                 BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "create_category_form";
        }

        String blogUrl = "/blog/" + principal.getName();

        try {
            categoryService.createCategory(categoryReqDto.getName(), blogUrl);
        } catch (DuplicatedCategoryNameException e) {
            bindingResult.rejectValue("name", "duplicatedName", "해당 카테고리가 이미 존재합니다.");
            return "create_category_form";
        }

        return "redirect:/category";
    }

    /* 카테고리 수정 Form */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{categoryId}")
    public String updateCategory(@PathVariable Long categoryId, Model model, Principal principal) {
        String blogUrl = "/blog/" + principal.getName();
        Category category = categoryService.getCategory(categoryId);
        CategoryReqDto categoryReqDto = new CategoryReqDto();

        if (!category.getBlogUrl().equals(blogUrl)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        if (category != null) {
            categoryReqDto.setName(category.getName());
        }

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryDto",  categoryReqDto);

        return "update_category_form";
    }

    /* 카테고리 수정 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{categoryId}")
    public String updateCategory(@PathVariable Long categoryId,
                                 @Valid @ModelAttribute("categoryDto") CategoryReqDto categoryReqDto,
                                 BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "update_category_form";
        }

        String blogUrl = "/blog/" + principal.getName();
        Category category = categoryService.getCategory(categoryId);

        if (!category.getBlogUrl().equals(blogUrl)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        try {
            categoryService.updateCategory(categoryId, categoryReqDto.getName(), blogUrl);
        } catch (DuplicatedCategoryNameException e) {
            bindingResult.rejectValue("name", "duplicatedName", "해당 카테고리가 이미 존재합니다.");
            return "update_category_form";
        }

        return "redirect:/category";
    }

    /* 카테고리 삭제 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId, Principal principal) {
        String blogUrl = "/blog/" + principal.getName();
        Category category = categoryService.getCategory(categoryId);

        if (!category.getBlogUrl().equals(blogUrl)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        categoryService.deleteCategory(categoryId);

        return "redirect:/category";
    }

    /* 블로그 카테고리 전체 조회 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    public String getCategoryList(Model model, Principal principal) {
        String blogUrl = "/blog/" + principal.getName();

        List<Category> categoryList = categoryService.getCategoryList(blogUrl);

        model.addAttribute("categoryList", categoryList);

        return "category_list";
    }
}
