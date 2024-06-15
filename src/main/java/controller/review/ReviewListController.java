package controller.review;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewListController {
    @GetMapping("/review")
    public String list() {
        return "thymeleaf/review";
    }
}
