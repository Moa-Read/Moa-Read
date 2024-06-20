package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.dto.account.request.SignupReqDto;
import dongduk.cs.moaread.exception.DuplicatedIdException;
import dongduk.cs.moaread.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class AccountController {
    private final AccountService accountService;

    /* 회원 가입 Form */
    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("signupDto", new SignupReqDto());
        return "signup_form";
    }

    /* 회원 가입 */
    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("signupDto") SignupReqDto signupReqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "signup_form";
        }

        try {
            accountService.signUp(signupReqDto);
        } catch (DuplicatedIdException e) {
            bindingResult.rejectValue("id", "duplicatedId", "중복된 아이디입니다.");
            return "signup_form";
        }

        return "redirect:/";
    }
}
