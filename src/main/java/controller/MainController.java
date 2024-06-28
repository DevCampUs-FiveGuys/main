package controller;

import data.dto.PortfolioDto;
import data.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/api/user/portfolio")
    public List<Map<String, Object>> getPortfolioDataMain() {
        List<Map<String, Object>> list = mainService.getPortfolioDataMain();
        System.out.println("mainController 작동");
        return list;
    }
}
