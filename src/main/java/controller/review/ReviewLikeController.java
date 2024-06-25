package controller.review;

import data.service.CheckListService;
import data.service.ReviewService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReviewLikeController {
    @NonNull
    private ReviewService reviewService;
    @NonNull
    private CheckListService checkListService;


    @PostMapping("/like/insert")
    public String insertLike(int rev_id) {
        checkListService.insertLike(rev_id);

        return "redirect:/review/list";
    }
}
