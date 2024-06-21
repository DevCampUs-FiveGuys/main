package controller.portfolio;

import data.dto.PortfolioDto;
import data.service.MemberService;
import data.service.PortfolioAnswerService;
import data.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioAnswerController {

    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private PortfolioAnswerService portfolioAnswerService;

    @GetMapping("/alist")
    public List<PortfolioDto> alist()
    {
        return portfolioAnswerService.getAnswerData();
    }



}
