package controller.portfolio;

import data.dto.replyDto;
import data.service.MemberService;
import data.service.PortfolioAnswerService;
import data.service.PortfolioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void insertAnswer(@RequestParam int num,
                             @RequestParam String comment)
    {
        replyDto dto = replyDto.builder().comment(comment).num(num).build();

        portfolioAnswerService.insertAnswer(dto);
    }

    @GetMapping("/alist")
    public List<replyDto> alist(@RequestParam int num)

    {
        return portfolioAnswerService.getAnswerData(num);
    }

    @GetMapping("/adelete")
    public void deleteAnswer(@RequestParam int reply_id)

    {
        portfolioAnswerService.deleteAnswer(reply_id);
    }
}
