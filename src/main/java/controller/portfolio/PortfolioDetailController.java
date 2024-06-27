package controller.portfolio;

import data.dto.PortfolioDto;
import data.mapper.PortfolioMapperInter;
import data.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PortfolioDetailController {

    @Autowired
    PortfolioService portfolioService;

    @GetMapping("/portfolio/Detail")
    public String detail(Model model,@RequestParam(required = false) Integer portfolio_id)
    {
        if (portfolio_id != null) {
            portfolioService.updateReadcount(portfolio_id);
            List<PortfolioDto> list = portfolioService.getPortfolioData(portfolio_id);
            PortfolioDto dto = portfolioService.getData(portfolio_id);

            model.addAttribute("portfolio_id", portfolio_id);
            model.addAttribute("list", list);
            model.addAttribute("dto",dto);
        } else {
            model.addAttribute("error", "Portfolio ID is required");
        }

        return "thymeleaf/portfolioDetail";
    }
}
