package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.dto.item.request.CreateItemReqDto;
import dongduk.cs.moaread.dto.item.request.UpdateItemReqDto;
import dongduk.cs.moaread.service.ItemService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    /* 아이템 등록 Form */
    @GetMapping("/create")
    public String createItemForm(Model model) {
        model.addAttribute("itemDto", new CreateItemReqDto());
        return "create_item_form";
    }

    /* 아이템 등록 */
    @PostMapping("/create")
    public String createItem(@Valid @ModelAttribute("itemDto") CreateItemReqDto itemReqDto,
                             BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "create_item_form";
        }

        if (!isAdmin(session)) {
            bindingResult.reject("authorization", "관리자 권한이 필요합니다.");
            return "create_item_form";
        }

        itemService.createItem(itemReqDto);
        return "redirect:/item/list";
    }

    /* 아이템 수정 Form */
    @GetMapping("/update/{id}")
    public String updateItemForm(@PathVariable int id, Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/item/list";
        }

        model.addAttribute("itemDto", itemService.getItemById(id));
        return "update_item_form";
    }

    /* 아이템 수정 */
    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable int id, @Valid @ModelAttribute("itemDto") UpdateItemReqDto itemReqDto,
                             BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "update_item_form";
        }

        if (!isAdmin(session)) {
            bindingResult.reject("authorization", "관리자 권한이 필요합니다.");
            return "update_item_form";
        }

        itemService.updateItem(id, itemReqDto);
        return "redirect:/item/list";
    }

    /* 아이템 삭제 */
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable int id, HttpSession session, Model model) {
        if (!isAdmin(session)) {
            model.addAttribute("authorizationError", "관리자 권한이 필요합니다.");
            return "item_list";
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

    private boolean isAdmin(HttpSession session) {
        Object role = session.getAttribute("userRole");
        return role != null && role.equals("ADMIN");
    }
}
