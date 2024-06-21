package controller.portfolio;

import data.dto.PortfolioDto;
import data.service.PortfolioAnswerService;
import data.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PortfolioListController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioAnswerService answerService;

    @GetMapping("/portfolio/list")
    public String list(Model model)
    {
        answerService.getAnswerData();
        List<PortfolioDto> list = answerService.getAnswerData();
        model.addAttribute("list", list);

        return "thymeleaf/portfolio";
    }
}
