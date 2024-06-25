package controller.portfolio;

import data.dto.PortfolioDto;
import data.mapper.PortfolioMapperInter;
import data.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PortfolioDetailController {

    @Autowired
    PortfolioService portfolioService;

    @GetMapping("portfolio/Detail")
    public String detail(Model model,@RequestParam(required = false) Integer portfolio_id)
    {
        if (portfolio_id != null) {
            portfolioService.updateReadcount(portfolio_id);
            List<PortfolioDto> list = portfolioService.getPortfolioData(portfolio_id);
            model.addAttribute("list", list);
        } else {
            model.addAttribute("error", "Portfolio ID is required");
        }

        return "thymeleaf/portfolioDetail";
    }
}
