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
    // review db에서 작성한 후기 등록
    public void insertReview(ReviewDto reviewDto) {
        reviewMapperInter.insertReview(reviewDto);
    }

    public List<ReviewDto> selectAllReview(String name, String num){
        return reviewMapperInter.selectAllReview(name, num);
    }

    // review db에서 입력한 순서대로 후기 나열(오름차순)
    public List<ReviewDto> getAllReview(){
        return reviewMapperInter.getAllReview();
    }

    // review db에서 선택한 review_id를 검색하여 해당하는 후기 삭제
    public void deleteReview(int review_id) {
        reviewMapperInter.deleteReview(review_id);
    }

    // review db에 있는 전체 평균 평점 구하기
    public  double getAvgStar() {
        return reviewMapperInter.getAvgStar();
    }

    // member db에서 성별에 따른 남,녀 성별 개수 count 하기
    public int countByGender(int gender){
        return reviewMapperInter.countByGender(gender);
    }

    // member db에 있는 전체 성별 개수 count 하기
    public int getTotalGender(){
        return reviewMapperInter.getTotalGender();
    }

    //과정명 선택시 과정명에 해당하는 기수명을 course db에서 불러오기
    public List<String> getNumOfCourse(String name){
        return courseMapperInter.getNumOfCourse(name);
    }

    //중복처리한 과정명을 course db에서 불러오기
    public List<CourseDto> getAllCourseList(){
        return courseMapperInter.getAllCourseList();
    }

}
