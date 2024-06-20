package data.service;

import data.dto.CourseDto;
import data.dto.ReviewDto;
import data.mapper.CourseMapperInter;
import data.mapper.ReviewMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  ReviewService {
    @Autowired
    private ReviewMapperInter reviewMapperInter;

    @Autowired
    private CourseMapperInter courseMapperInter;


    public void insertReview(ReviewDto reviewDto) {
        reviewMapperInter.insertReview(reviewDto);
    }

    public List<ReviewDto> selectAllReview(String name, String num){
        return reviewMapperInter.selectAllReview(name, num);
    }

    public List<ReviewDto> getAllReview(){
        return reviewMapperInter.getAllReview();
    }

    public void deleteReview(int review_id) {
        reviewMapperInter.deleteReview(review_id);
    }

    public  double getAvgStar() {
        return reviewMapperInter.getAvgStar();
    }
//    public int getLikeCount(int review_id) {
//        return reviewMapperInter.getLikeCount(review_id);
//    }

    public int countByGender(int gender){
        return reviewMapperInter.countByGender(gender);
    }

    public int getTotalGender(){
        return reviewMapperInter.getTotalGender();
    }

    public List<String> getNumOfCourse(String name){
        return courseMapperInter.getNumOfCourse(name);
    }

    public List<CourseDto> getAllCourseList(){
        return courseMapperInter.getAllCourseList();
    }

    public List<String> getMemberName(String name, String num){
        return reviewMapperInter.getMemberName(name, num);
    }

}
