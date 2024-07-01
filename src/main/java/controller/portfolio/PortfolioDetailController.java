package controller.portfolio;

import data.dto.PortfolioDto;
import data.mapper.PortfolioMapperInter;
import data.naver.cloud.NcpObjectStorageService;
import data.service.MemberService;
import data.service.PortfolioAnswerService;
import data.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class PortfolioDetailController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private NcpObjectStorageService storageService;

    private String bucketName = "bitcamp-bucket-149";
    private String folderName = "semiproject";

    @GetMapping("/portfolio/list/Detail")
    public String detail(Model model,
                         @RequestParam int portfolio_id,
                         Authentication authentication)
    {

            PortfolioDto dto = portfolioService.getData(portfolio_id);

        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            String userName = memberService.findByUsername(email).getName();

            portfolioService.updateReadcount(portfolio_id);

            model.addAttribute("portfolio_id", portfolio_id);
            model.addAttribute("dto", dto);
            model.addAttribute("member_id", member_id);
            model.addAttribute("userName", userName);

        } else {

            model.addAttribute("dto", dto != null ? dto : new PortfolioDto());
            model.addAttribute("member_id", -1);
            model.addAttribute("userName", "Anonymous");
        }
            return "thymeleaf/portfolioDetail";
    }

    @GetMapping("/portfolio/delete")
    public String delete(@RequestParam int portfolio_id)
    {
        PortfolioDto dto = portfolioService.getData(portfolio_id);
        if (dto == null) {
            return "redirect:/portfolio/list?error=notFound";
        }

        String photo = dto.getFile_name();
        if (photo != null && !photo.isEmpty()) {
            try {
                storageService.deleteFile(bucketName, folderName, photo);
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/portfolio/list?error=photoDeleteFailed";
            }
        }

        // 관련된 모든 댓글을 먼저 삭제합니다.
        portfolioService.deleteRepliesByPortfolioId(portfolio_id);

        // 포트폴리오를 삭제합니다.
        portfolioService.deletePortfolio(portfolio_id);

        return "redirect:/portfolio/list";
    }
}
