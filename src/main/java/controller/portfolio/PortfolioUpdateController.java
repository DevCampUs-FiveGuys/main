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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/portfolio/insert")
    public String insertPortfolio(
            @ModelAttribute PortfolioDto dto,
            @RequestParam("upload") MultipartFile upload,
            Authentication authentication,
            Model model) {

        String email = authentication.getName();
        int member_id = memberService.findByUsername(email).getMember_id();
        String userName = memberService.findByUsername(email).getName();
        dto.setMember_id(member_id);

        String photo = storageService.uploadFile(bucketName, folderName, upload);
        dto.setFile_name(photo);

        portfolioService.insertPortfolio(dto);

        return "redirect:/portfolio/list";
    }

    @GetMapping("/portfolio/updateform")
    public String updateform(int portfolio_id, Model model) {

        PortfolioDto dto = portfolioService.getData(portfolio_id);
        model.addAttribute("dto", dto);
        return "thymeleaf/portfolioDetailUpdate";
    }

    @PostMapping("/portfolio/update")
    public String update(@ModelAttribute PortfolioDto dto,
                         @RequestParam("upload") MultipartFile upload) {

        if (upload != null && !upload.isEmpty()) {
            try {
                if (dto.getFile_name() != null) {
                    String existphoto = dto.getFile_name();
                    storageService.deleteFile(bucketName, folderName, existphoto);
                }

                String photo = storageService.uploadFile(bucketName, folderName, upload);
                dto.setFile_name(photo);

            } catch (Exception e) {
                e.printStackTrace();

                return "redirect:/portfolio/update?error=photoUploadFailed";
            }

        }
        portfolioService.updatePortfolio(dto);
        return "redirect:/portfolio/list/Detail?portfolio_id=" + dto.getPortfolio_id();
    }

}