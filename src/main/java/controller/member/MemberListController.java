package controller.member;

import data.dto.MemberDto;
import data.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
}
