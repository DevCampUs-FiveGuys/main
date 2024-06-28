package controller.member;

import data.dto.MemberDto;
import data.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberListController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/user/join")
    public String join(@ModelAttribute MemberDto memberDto) {
        memberService.insertMember(memberDto);
        return "redirect:/";
    }

    // 아이디 중복 체크 (email)
    @ResponseBody
    @GetMapping("/api/user/checkID")
    public Map<String, Integer> checkID(@RequestParam("searchID") String searchID) {
        Map<String, Integer> map = new HashMap<>();
        int count = memberService.checkID(searchID);
        map.put("count", count);
        return map;
    }

    @GetMapping("/api/user/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        /* 에러와 예외를 모델에 담아 view resolve */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "thymeleaf/login";
    }

    @GetMapping("/student/mypage")
    public String studentMypage(Model model) {
        model.addAttribute("page", "attendance");
        return "thymeleaf/student/attendance";
    }


    @GetMapping("/mypage")
    public String myPage(Authentication authentication, Model model) {
        if (authentication != null) {
            String role = authentication.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .findFirst()
                    .orElse("");

            if (role.equals("ROLE_STUDENT")) {
                return "redirect:/student/mypage";
            } else if (role.equals("ROLE_TEACHER")) {
                return "redirect:/teacher/mypage";
            } else if (role.equals("ROLE_ADMIN")) {
                return "redirect:/admin/mypage";
            }
        }
        return "redirect:/login";
    }
}
