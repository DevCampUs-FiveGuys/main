package data.service;

import data.dto.ReviewDto;
import data.mapper.ReviewMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public int deleteReview(int review_id) {
        return reviewMapperInter.deleteReview(review_id);
    }
    public  double getAvgStar() {
        return reviewMapperInter.getAvgStar();
    }

}
