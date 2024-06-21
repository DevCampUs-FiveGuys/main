package controller.portfolio;

import data.dto.PortfolioDto;
import data.mapper.PortfolioMapperInter;
import data.service.PortfolioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PortfolioUpdateController {

    @NonNull
    private PortfolioService portfolioService;

    @NonNull
    private PortfolioMapperInter portfolioMapperInter;

    @PostMapping("/portfolio/update")
    public String updatePortfolio(@ModelAttribute PortfolioDto dto,
                                  @RequestParam("upload") MultipartFile upload,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {

                // PortfolioDto에 파일 경로 설정 (필요시)
                dto.setFile_name(dto.getFile_name());

            // 포트폴리오 서비스 호출
            portfolioService.updatePortfolio(dto);

        // 포트폴리오 상세 페이지로 리디렉션
        return "redirect:/portfolioDetail?portfolio_id=" + dto.getPortfolio_id();
    }

    @GetMapping("/portfolio/write")
    public String portfolioWrite(Model model)
    {
        PortfolioDto dto = new PortfolioDto();
        model.addAttribute("PortfolioDto", dto);

        return "thymeleaf/portfolioWrite";
    }

    @PostMapping("/portfolio/insert")
    public String insertPortfolio(
            @ModelAttribute PortfolioDto dto)
    {
        portfolioService.insertPortfolio(dto);

        return "redirect:/portfolioDetail";
    }

}