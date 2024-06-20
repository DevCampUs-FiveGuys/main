package data.mapper;

import data.dto.ReviewDto;
import org.apache.ibatis.annotations.*;

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
    public void deleteReview(int review_id);
    @Select ("select round(avg(star),1) from review")
    public  double getAvgStar();


//    @Select("select `like` from review where review_id=#{review_id}")
//    public int getLikeCount(int review_id);

    // 성별 개수 count 하기
    @Select("select count(*) from member where gender=#{gender}")
    public int countByGender(int gender);
    @Select("select count(*) from member")
    public int getTotalGender();
    @Update("update review set `like`=#{}")
    public int updateLike();

}
