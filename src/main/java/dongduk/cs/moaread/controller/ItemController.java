package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.dto.item.request.CreateItemReqDto;
import dongduk.cs.moaread.dto.item.request.UpdateItemReqDto;
import dongduk.cs.moaread.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    /* 아이템 등록 Form */
    @GetMapping("/create")
    public String createItemForm(Model model, Principal principal) {
        if (!isAdmin(principal)) {
            return "error/unauthorized";
        }
        model.addAttribute("itemDto", new CreateItemReqDto());
        return "create_item_form";
    }

    /* 아이템 등록 */
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createItem(@Valid @ModelAttribute("itemDto") CreateItemReqDto itemReqDto,
                             BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "create_item_form";
        }

        if (!isAdmin(principal)) {
            return "error/unauthorized";
        }

        itemService.createItem(itemReqDto);
        return "redirect:/item/list";
    }

    /* 아이템 수정 Form */
    @GetMapping("/update/{id}")
    public String updateItemForm(@PathVariable int id, Model model, Principal principal) {
        if (!isAdmin(principal)) {
            return "error/unauthorized";
        }

        model.addAttribute("itemDto", itemService.getItemById(id));
        return "update_item_form";
    }

    /* 아이템 수정 */
    @PostMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateItem(@PathVariable int id, @Valid @ModelAttribute("itemDto") UpdateItemReqDto itemReqDto,
                             BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "update_item_form";
        }

        if (!isAdmin(principal)) {
            return "error/unauthorized";
        }

        itemService.updateItem(id, itemReqDto);
        return "redirect:/item/list";
    }

    /* 아이템 삭제 */
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteItem(@PathVariable int id, Principal principal) {
        if (!isAdmin(principal)) {
            return "error/unauthorized";
        }

        itemService.deleteItem(id);
        return "redirect:/item/list";
    }

    /* 아이템 목록 조회 */
    @GetMapping("/list")
    public String listItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "item_list";
    }

    private boolean isAdmin(Principal principal) {
        return principal != null && principal.getName().equals("admin");
    }
}
