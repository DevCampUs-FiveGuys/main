package controller.portfolio;

import data.dto.MemberDto;
import data.dto.replyDto;
import data.service.MemberService;
import data.service.PortfolioAnswerService;
import data.service.PortfolioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioAnswerController {

    @Autowired
    private PortfolioAnswerService portfolioAnswerService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/ainsert")
    public void insertAnswer(@RequestParam int portfolio_id,
                             @RequestParam String comment,
                             Authentication authentication,
                             Model model)
    {

        String email = authentication.getName();
        int member_id = memberService.findByUsername(email).getMember_id();
        String userName = memberService.findByUsername(email).getName();

        model.addAttribute("member_id", member_id);
        model.addAttribute("userName", userName);

        replyDto dto = replyDto.builder()
                .comment(comment)
                .portfolio_id(portfolio_id)
                .member_id(member_id)
                .member_id(memberService.findByUsername(authentication.getName()).getMember_id())
                .build();

        portfolioAnswerService.insertAnswer(dto);

    }

    @GetMapping("/alist")
    public List<replyDto> getAnswers(@RequestParam int portfolio_id) {
        return portfolioAnswerService.getAllRepliesByPortfolioId(portfolio_id);
    }

    @GetMapping("/adelete")
    public void deleteAnswer(@RequestParam int reply_id)
    {
        portfolioAnswerService.deleteAnswer(reply_id);
    }
}
