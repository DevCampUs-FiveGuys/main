package controller.portfolio;

import data.dto.MemberDto;
import data.dto.PortfolioDto;
import data.mapper.MemberMapperInter;
import data.mapper.PortfolioMapperInter;
import data.naver.cloud.NcpObjectStorageService;
import data.service.MemberService;
import data.service.PortfolioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PortfolioUpdateController {

    @NonNull
    private PortfolioService portfolioService;
    @Autowired
    private MemberService memberService;

    @NonNull
    private PortfolioMapperInter portfolioMapperInter;

    @Autowired
    private MemberMapperInter memberMapperInter;

    private String bucketName = "bitcamp-bucket-149";
    private String folderName = "semiproject";

    @Autowired
    private NcpObjectStorageService storageService;

    @GetMapping("/portfolio/write")
    public String portfolioWrite(
            /*@RequestParam(defaultValue = "0") int portfolio_id,
            @RequestParam(defaultValue = "0") int regroup,
            @RequestParam(defaultValue = "0") int restep,
            @RequestParam(defaultValue = "0") int relevel,
            @RequestParam(defaultValue = "1") int currentPage,*/
            Model model
    )

    {
        /*String title = "";
        if(portfolio_id>0) {
            title = "[답글]"+portfolioService.getData(portfolio_id).getTitle();
        }*/
        PortfolioDto dto = new PortfolioDto();
        model.addAttribute("PortfolioDto", dto);
        /*model.addAttribute("portfolio_id", portfolio_id);
        model.addAttribute("regroup", regroup);
        model.addAttribute("restep", restep);
        model.addAttribute("relevel", relevel);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("title", title);*/

        return "thymeleaf/portfolioWrite";
    }

    @PostMapping("/portfolio/insert")
    public String insertPortfolio(
            @ModelAttribute PortfolioDto dto,
            @RequestParam("upload") MultipartFile upload,
            HttpServletRequest request
    )

    {
        String photo = storageService.uploadFile(bucketName, folderName, upload);
        dto.setFile_name(photo);

        portfolioService.insertPortfolio(dto);

        return "redirect:/portfolio/list";
    }

}