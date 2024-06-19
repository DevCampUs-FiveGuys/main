package data.service;

import data.dto.ReviewDto;
import data.mapper.ReviewMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewMapperInter reviewMapperInter;


    public void insertReview(ReviewDto reviewDto) {
        reviewMapperInter.insertReview(reviewDto);
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

}
