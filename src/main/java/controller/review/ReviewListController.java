package controller.review;

import data.dto.ReviewDto;
import data.service.ReviewService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewListController {
    @NonNull
   private ReviewService reviewService;

    @GetMapping("/review/list")
    public String getReviews(Model model) {
        // Fetch reviews from the database
        List<ReviewDto> reviewlist = reviewService.getAllReview();

        ReviewDto reviewDto = new ReviewDto();
        model.addAttribute("reviewDto", reviewDto);
        // Add reviews to the model
        model.addAttribute("reviewlist", reviewlist);

        return "thymeleaf/review"; // Return the name of the Thymeleaf template
    }

    @GetMapping("/review/insert")
    public String insertReview(@ModelAttribute ReviewDto reviewDto) {
        System.out.println(reviewDto);
        reviewService.insertReview(reviewDto);

        return "redirect:/review/list";
    }

    @GetMapping("/review/delete")
    public String deleteReview(int review_id) {
        reviewService.deleteReview(review_id);

        return "redirect:/review/list";
    }


}
