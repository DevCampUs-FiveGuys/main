package controller.review;

import data.service.CheckListService;
import data.service.ReviewService;
import lombok.NonNull;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewLikeController {
    @NonNull
    private ReviewService reviewService;
    @NonNull
    private CheckListService checkListService;


    @PostMapping("/like/insert")
    public String insertLike(int mem_id, int rev_id) {
        checkListService.insertLike(mem_id, rev_id);

        return "redirect:/review/list";
    }

    @GetMapping("/like/delete")
    public String deleteLike(int like_id) {
        checkListService.deleteLike(like_id);

        return "redirect:/review/list";
    }
}
