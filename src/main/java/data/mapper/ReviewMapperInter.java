package data.mapper;

import data.dto.ReviewDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapperInter {

    @Insert("insert into review (content, star, created_at) values (#{content}, #{star}, now())")
    public void insertReview(ReviewDto reviewDto);
    @Select("select * from review order by review_id asc")
    public List<ReviewDto> getAllReview();
    @Select("select * from review where review_id=#{review_id}")
    public ReviewDto getReiewData(int review_id);
    @Delete("delete from review where review_id=#{review_id}")
    public int deleteReview(int review_id);
    @Select ("select round(avg(star),1) from review")
    public  double getAvgStar();

}
