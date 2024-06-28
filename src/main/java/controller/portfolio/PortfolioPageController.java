package controller.portfolio;

import data.dto.PortfolioDto;
import data.service.PortfolioAnswerService;
import data.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/portfolio")
public class PortfolioPageController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioAnswerService portfolioAnswerService;

    @GetMapping("/list")
    public String list(
            @RequestParam(defaultValue = "1") int currentPage,
            Model model)
    {
        int totalCount = portfolioService.getTotalCount();
        int perPage = 8;
        int perBlock = 5;
        int start;
        int startPage = 0;
        int totalPage;
        int endPage = 0;
        int no;

        totalPage = totalCount / perPage + (totalCount % perPage > 0 ? 1 : 0);
        startPage = (currentPage - 1) / perBlock * perBlock + 1;
        endPage = startPage + perBlock - 1;

        if (endPage > totalPage)
            endPage = totalPage;

        start = (currentPage - 1) * perPage;
        no = totalCount - (currentPage - 1) * perPage;

        List<PortfolioDto> list = portfolioService.getPagingList(start, perPage);

        for(PortfolioDto dto : list)
        {
            dto.setRecount(portfolioAnswerService.getAnswerData(dto.getPortfolio_id()).size());
        }

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("list", list);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("no", no);
        model.addAttribute("page", "portfolio");


        return "thymeleaf/portfolio";
    }
}
