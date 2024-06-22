package dongduk.cs.moaread.controller;

import dongduk.cs.moaread.domain.Application;
import dongduk.cs.moaread.dto.application.request.CreateApplicationReqDto;
import dongduk.cs.moaread.service.ApplicationService;
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
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    /* 중고 도서 판매 신청 Form */
    @GetMapping("/create")
    public String createApplicationForm(Model model) {
        model.addAttribute("applicationDto", new CreateApplicationReqDto());
        return "create_application_form";
    }

    /* 중고 도서 판매 신청 */
    @PostMapping("/create")
    public String createApplication(@Valid @ModelAttribute("applicationDto") CreateApplicationReqDto applicationReqDto,
                                    BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "create_application_form";
        }

        String username = principal.getName();
        applicationReqDto.setUserId(username);

        applicationService.createApplication(applicationReqDto);
        return "redirect:/application/list";
    }

    /* 중고 도서 판매 신청 취소 */
    @GetMapping("/cancel/{id}")
    public String cancelApplication(@PathVariable int id, Principal principal) {
        String username = principal.getName();

        if (!applicationService.isOwnedByUser(id, username) && !username.equals("admin")) {
            return "error/unauthorized";
        }

        applicationService.cancelApplication(id);
        return "redirect:/application/list";
    }

    /* 중고 도서 판매 신청 내역 전체 조회 */
    @GetMapping("/list")
    public String listApplications(Model model, Principal principal) {
        String username = principal.getName();
        List<Application> applications;

        if (username.equals("admin")) {
            applications = applicationService.getAllApplications();
        } else {
            applications = applicationService.getApplicationsByUser(username);
        }

        model.addAttribute("applications", applications);
        return "application_list";
    }

    /* 중고 도서 판매 신청 내역 상세 조회 */
    @GetMapping("/detail/{id}")
    public String detailApplication(@PathVariable int id, Model model, Principal principal) {
        String username = principal.getName();
        Application application = applicationService.getApplicationById(id);

        if (!application.getUserId().equals(username) && !username.equals("admin")) {
            return "error/unauthorized";
        }

        model.addAttribute("application", application);
        return "application_detail";
    }

    /* 관리자 기능 - 중고 도서 판매 신청 심사 */
    @PostMapping("/review/{id}")
    @PreAuthorize("isAuthenticated()")
    public String reviewApplication(@PathVariable int id, @RequestParam String status, Principal principal) {
        if (!principal.getName().equals("admin")) {
            return "error/unauthorized";
        }

        applicationService.reviewApplication(id, status);
        return "redirect:/application/admin/list";
    }

    /* 관리자 기능 - 중고 도서 판매 신청 내역 전체 조회 */
    @GetMapping("/admin/list")
    @PreAuthorize("isAuthenticated()")
    public String listApplicationsAdmin(Model model, Principal principal) {
        if (!principal.getName().equals("admin")) {
            return "error/unauthorized";
        }

        List<Application> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);
        return "admin_application_list";
    }
}
