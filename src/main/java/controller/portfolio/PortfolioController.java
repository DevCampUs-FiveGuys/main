package controller.portfolio;

import data.dto.PortfolioDto;
import data.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/insert")
    public String insertPortfolio(
            @ModelAttribute PortfolioDto dto,
            @RequestParam("upload")MultipartFile upload,
            HttpRequest request
            )
    {
        portfolioService.insertPortfolio(dto);
        return "redirect:/portfolio?portfolio_id=" + dto.getPortfolio_id();
    }

}