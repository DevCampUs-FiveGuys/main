package controller.review;

import data.dto.CourseDto;
import data.dto.ReviewDto;
import data.service.CheckListService;
import data.service.MemberService;
import data.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private MemberService memberService;

    @GetMapping("/review/list")
    public String getReviews(
            Model model) {
        // 모든 리뷰를 reviewlist에 추가
        List<ReviewDto> reviewlist = reviewService.getAllReview();

        // 모든 과정명,기수명을 courselist에 추가
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

        // 평균 평점 계산 영역
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

        Map<String, String> starRangePercentageMap = starRangeCountMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.format("%.1f", ((double) entry.getValue() / reviewlist.size()) * 100)
                ));

        Map<String, String> sortedStarRangePercentageMap = new LinkedHashMap<>();
        List<String> sortedKeys = Arrays.asList("4.5-5", "3.5-4", "2.5-3", "1.5-2", "0.5-1");
        for (String key : sortedKeys) {
            sortedStarRangePercentageMap.put(key, starRangePercentageMap.getOrDefault(key, "0.0"));
        }

        ReviewDto reviewDto = new ReviewDto();
        model.addAttribute("reviewDto", reviewDto);
        model.addAttribute("reviewlist", reviewlist);
        model.addAttribute("reviewAvg", reviewService.getAvgStar());
        model.addAttribute("starRangePercentageMap", sortedStarRangePercentageMap);
        model.addAttribute("malePercentage", malePercentage);
        model.addAttribute("femalePercentage", femalePercentage);
        model.addAttribute("courselist", courselist);
        model.addAttribute("page", "review");

        return "thymeleaf/review"; // Return the name of the Thymeleaf template
    }

    // 후기 추가
    @PostMapping("/review/insert")
    public String insertReview(@ModelAttribute ReviewDto reviewDto,@ModelAttribute("member_id") int member_id) {
        reviewDto.setMember_id(member_id);
        reviewService.insertReview(reviewDto);

        return "redirect:/review/list";
    }

    // 후기 삭제
    @GetMapping("/review/delete")
    public String deleteReview(int review_id) {
        reviewService.deleteReview(review_id);

        return "redirect:/review/list";
    }

    // 후기 좋아요
    @PostMapping("/review/like")
    public String updateLike(@RequestParam("reviewid") int review_id) {

        reviewService.updateLike(review_id);

        return "redirect:/review/list";
    }


    //과정명 선택시 get mapping으로 과정명에 해당하는 기수명을 course db에서 불러오기
    @GetMapping("/review/list/names")
    @ResponseBody
    public List<String> getCourseNums(@RequestParam("name") String name){
        return reviewService.getNumOfCourse(name);
    }
    @GetMapping("/review/list/nums")
    @ResponseBody
    public List<ReviewDto> selectAllReview(
            @RequestParam("name") String name,
            @RequestParam("num") String num, Model model) {

        List<ReviewDto> selectreviewlist = reviewService.selectAllReview(name, num);

        Map<String, Long> selectedAvgMap = selectreviewlist.stream()
                .collect(Collectors.groupingBy(
                        review -> {
                            double selectedStar = review.getStar();
                            if (selectedStar == 0.5 || selectedStar == 1.0) {
                                return "0.5-1";
                            } else if (selectedStar == 1.5 || selectedStar == 2.0) {
                                return "1.5-2";
                            } else if (selectedStar == 2.5 || selectedStar == 3.0) {
                                return "2.5-3";
                            } else if (selectedStar == 3.5 || selectedStar == 4.0) {
                                return "3.5-4";
                            } else if (selectedStar == 4.5 || selectedStar == 5.0) {
                                return "4.5-5";
                            } else {
                                return "Other";
                            }
                        }, Collectors.counting()
                ));
        Map<String, String> starRangePercentageMap = selectedAvgMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.format("%.1f", ((double) entry.getValue() / selectreviewlist.size()) * 100)
                ));

        Map<String, String> selectedAvgPercentageMap = new LinkedHashMap<>();
        List<String> sortedKeys = Arrays.asList("4.5-5", "3.5-4", "2.5-3", "1.5-2", "0.5-1");
        for (String key : sortedKeys) {
            selectedAvgPercentageMap.put(key, starRangePercentageMap.getOrDefault(key, "0.0"));
        }

        model.addAttribute("selectedAvgPercentageMap", selectedAvgPercentageMap);


        return selectreviewlist;
    }

    // 사용자 이메일로부터 멤버 아이디 받아오기
    @ModelAttribute
    public void member_id (Authentication authentication, Model model) {

        if(authentication != null) {
            String email = authentication.getName(); // 인증된 사용자의 이메일

            int member_id = memberService.findByUsername(email).getMember_id();// 인증된 사용자의 이메일이 가지고 있는 member_id
            System.out.println(member_id);
            model.addAttribute("member_id", member_id);
        }
    }
}
