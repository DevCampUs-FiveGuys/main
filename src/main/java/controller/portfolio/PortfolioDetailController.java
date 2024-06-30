package controller.portfolio;

import data.dto.PortfolioDto;
import data.mapper.PortfolioMapperInter;
import data.service.MemberService;
import data.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PortfolioDetailController {

    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private MemberService memberService;

    @GetMapping("/portfolio/Detail")
    public String detail(Model model,
                         @RequestParam int portfolio_id,
                         Authentication authentication)
    {
        String email = authentication.getName();
        int member_id = memberService.findByUsername(email).getMember_id();
        System.out.println(member_id);
        String userName = memberService.findByUsername(email).getName();

            portfolioService.updateReadcount(portfolio_id);
            PortfolioDto dto = portfolioService.getData(portfolio_id);

            model.addAttribute("portfolio_id", portfolio_id);
            model.addAttribute("dto",dto);
            model.addAttribute("member_id", member_id);
            model.addAttribute("userName", userName);

        return "thymeleaf/portfolioDetail";
    }

    @GetMapping("/portfolio/delete")
    public String delete(@RequestParam int portfolio_id)
    {
        portfolioService.deletePortfolio(portfolio_id);

        return "redirect:/portfolio/list";
    }
}
