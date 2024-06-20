package controller.review;

import data.dto.CourseDto;
import data.dto.ReviewDto;
import data.service.CheckListService;
import data.service.ReviewService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReviewListController {
    @NonNull
    private ReviewService reviewService;
    @NonNull
    private CheckListService checkListService;


    @GetMapping("/review/list")
    public String getReviews(
            Model model) {
        // Fetch reviews from the database

        List<ReviewDto> reviewlist = reviewService.getAllReview();

        List<CourseDto> courselist = reviewService.getAllCourseList();

        // 멤버 전체 명수
        int totalGender = reviewService.getTotalGender();
        // 남자 멤버
        int maleCount = reviewService.countByGender(0);
        // 여자 멤버
        int femaleCount = reviewService.countByGender(1);

        // 남여 비율 계산
        String malePercentage = String.format("%.1f", ((double) maleCount / totalGender * 100));
        String femalePercentage = String.format("%.1f", ((double) femaleCount / totalGender * 100));

        // Count the number of reviews in each star range
        Map<String, Long> starRangeCountMap = reviewlist.stream()
                .collect(Collectors.groupingBy(
                        review -> {
                            double star = review.getStar();
                            if (star == 0.5 || star == 1.0) {
                                return "0.5-1";
                            } else if (star == 1.5 || star == 2.0) {
                                return "1.5-2";
                            } else if (star == 2.5 || star == 3.0) {
                                return "2.5-3";
                            } else if (star == 3.5 || star == 4.0) {
                                return "3.5-4";
                            } else if (star == 4.5 || star == 5.0) {
                                return "4.5-5";
                            } else {
                                return "Other";
                            }
                        }, Collectors.counting()
                ));

        // Calculate the percentages for each range
        Map<String, String> starRangePercentageMap = starRangeCountMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.format("%.1f", ((double) entry.getValue() / reviewlist.size()) * 100)
                ));

        // Create a LinkedHashMap to maintain the order
        Map<String, String> sortedStarRangePercentageMap = new LinkedHashMap<>();
        List<String> sortedKeys = Arrays.asList("4.5-5", "3.5-4", "2.5-3", "1.5-2", "0.5-1");
        for (String key : sortedKeys) {
            sortedStarRangePercentageMap.put(key, starRangePercentageMap.getOrDefault(key, "0.0"));
        }

        ReviewDto reviewDto = new ReviewDto();
        model.addAttribute("reviewDto", reviewDto);
        // Add reviews to the model
        model.addAttribute("reviewlist", reviewlist);
        model.addAttribute("reviewAvg", reviewService.getAvgStar());
        model.addAttribute("starRangePercentageMap", sortedStarRangePercentageMap);
        model.addAttribute("malePercentage", malePercentage);
        model.addAttribute("femalePercentage", femalePercentage);
        model.addAttribute("courselist", courselist);

//        model.addAttribute("likeCount", checkListService.ShowCountLike(reviewService.getid()));
//        model.addAttribute("reviewLikeCount", reviewService.getLikeCount(review_id));
        //checklike dto를 받아와 어차피 dto 안에 rev_id가 있는데 이게 reviewlist의 review_id랑 같은 거잖아?
        //이거를 == 써가지고 같으면 checklike의 showcountlike를 사용하면 되지 않을까?



        return "thymeleaf/review"; // Return the name of the Thymeleaf template
    }

    @PostMapping("/review/insert")
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

    @GetMapping("/review/names")
    @ResponseBody
    public List<String> getCourseNums(@RequestParam("name") String name){
        return reviewService.getNumOfCourse(name);
    }

    @GetMapping("/review/nums")
    @ResponseBody
    public String selectAllReview(
            @RequestParam("name") String name,
            @RequestParam("num") String num,
            Model model){

        List<ReviewDto> selectreviewlist = reviewService.selectAllReview(name,num);

        model.addAttribute("selectlist", selectreviewlist);

        return "redirect:/review/list";
    }

}
