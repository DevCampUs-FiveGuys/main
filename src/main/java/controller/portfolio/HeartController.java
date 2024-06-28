package controller.portfolio;


import data.dto.HeartDto;
import data.service.HeartService;
import data.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/portfolio")
public class HeartController {

    @Autowired
    private HeartService heartService;

    @Autowired
    private MemberService memberService;

    // 포트폴리오 찜하기 버튼을 누르면 DB 에 저장
    @PostMapping("/heart/insert")
    @ResponseBody
    public void insertHeart(@RequestParam int portfolio_id, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            heartService.insertHeart(member_id, portfolio_id);
        }
    }

    // 포트폴리오 찜하기 버튼이 눌린 상태에서 또 다시 누르면 DB 에서 삭제
    @DeleteMapping("/heart/delete")
    @ResponseBody
    public void deleteHeart(@RequestParam int portfolio_id, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            heartService.deleteHeart(member_id, portfolio_id);
        }
    }

    // 찜한 포트폴리오를 조회 (찜한 포트폴리오가 있는지 확인)
    @GetMapping("/heart/get")
    @ResponseBody
    public List<HeartDto> getHeart(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            return heartService.getHeart(member_id);
        }
        return null;
    }
}
