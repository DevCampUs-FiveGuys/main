package controller.faq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class FaqListController {


    @GetMapping("/faqlist")
    public String faqlist()
    {
        return "thymeleaf/faqlist";
    }
}
