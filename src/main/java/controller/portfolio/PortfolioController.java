package controller.portfolio;

import data.dto.PortfolioDto;
import data.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/insert")
    public String insertPortfolio(@ModelAttribute PortfolioDto dto) {
        portfolioService.insertPortfolio(dto);
        return "redirect:/portfolio?portfolio_id=" + dto.getPortfolio_id();
    }

}
