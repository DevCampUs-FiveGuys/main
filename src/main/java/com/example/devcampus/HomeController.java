package com.example.devcampus;

import data.dto.CourseDto;
import data.dto.mainPortfolioDto;
import data.dto.mainReviewDto;
import data.service.MainService;
import data.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ReviewService reviewService;
    private final MainService mainService;
    @GetMapping("/")
    public String home(Model model) {
        List<mainPortfolioDto> portfolioList = mainService.getPortfolioDataMain();
        List<mainReviewDto> reviewList = mainService.getReviewDataMain();
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("portfolioList", portfolioList);
        model.addAttribute("page", "home");
        return "main";
    }
    @GetMapping("/signup")
    public String signup(Model model) {
        List<CourseDto> courselist = reviewService.getAllCourseList();
        model.addAttribute("courselist", courselist);
        model.addAttribute("page","signup");
        return "thymeleaf/signup";
    }
    @GetMapping("/signup/list/names")
    @ResponseBody
    public List<String> getCourseNums(@RequestParam("name") String name){
        return reviewService.getNumOfCourse(name);
    }
    @GetMapping("/login")
    public String login() {
        return "thymeleaf/login";
    }
    @GetMapping("/portfolioDetail")
    public String portfolioDetail() {
        return "thymeleaf/portfolioDetail";
    }

    @GetMapping("/portfolio/portfolioWrite")
    public String portfolioWrite() {
        return "thymeleaf/portfolioWrite";
    }
}
