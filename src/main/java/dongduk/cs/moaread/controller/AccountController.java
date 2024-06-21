package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.dto.account.request.SignupReqDto;
import dongduk.cs.moaread.dto.account.request.UpdateReqDto;
import dongduk.cs.moaread.dto.account.response.ProfileResDto;
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

import java.security.Principal;

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

    /* 로그인 */
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    /* 프로필 조회 */
    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        String id = principal.getName();

        if (id == null) {
            return "login_form";
        }

        ProfileResDto profileResDto = accountService.getProfile(id);
        model.addAttribute("profile", profileResDto);

        return "profile";
    }

    /* 회원 정보 수정 Form */
    @GetMapping("/update")
    public String updateProfile(Model model, Principal principal) {
        String id = principal.getName();

        if (id == null) {
            return "login_form";
        }

        ProfileResDto profileResDto = accountService.getProfile(id);

        UpdateReqDto updateReqDto = new UpdateReqDto();
        updateReqDto.setName(profileResDto.getName());
        updateReqDto.setPhone(profileResDto.getPhone());
        updateReqDto.setEmail(profileResDto.getEmail());
        updateReqDto.setAddress(profileResDto.getAddress());
        updateReqDto.setDetailedAddress(profileResDto.getDetailedAddress());
        updateReqDto.setZip(profileResDto.getZip());

        model.addAttribute("updateDto", updateReqDto);

        return "update_form";
    }

    /* 회원 정보 수정 */
    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("updateDto") UpdateReqDto updateReqDto, BindingResult bindingResult, Principal principal) {
        String id = principal.getName();

        if (id == null) {
            return "login_form";
        }

        if (bindingResult.hasErrors()) {
            return "update_form";
        }

        accountService.updateProfile(id, updateReqDto);

        return "redirect:/user/profile";
    }

    /* 회원 탈퇴 */
    @GetMapping("/withdraw")
    public String withdraw(Principal principal) {
         String id = principal.getName();

         if (id == null) {
             return "login_form";
         }

         accountService.withdraw(id);

         return "redirect:/user/logout";
    }
}
