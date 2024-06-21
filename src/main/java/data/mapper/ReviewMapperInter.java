package data.mapper;

import data.dto.ReviewDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapperInter {

    // review db에서 작성한 후기 등록
    @Insert("insert into review (content, star, created_at) values (#{content}, #{star}, now())")
    public void insertReview(ReviewDto reviewDto);

    // review db에서 입력한 순서대로 후기 나열(오름차순)
    @Select("select * from review order by review_id asc")
    public List<ReviewDto> getAllReview();

    // review db에서 선택한 review_id를 검색하여 해당하는 후기의 모든 내용 불러오기
    @Select("select * from review where review_id=#{review_id}")
    public ReviewDto getReiewData(int review_id);

    // review db에서 선택한 review_id를 검색하여 해당하는 후기 삭제
    @Delete("delete from review where review_id=#{review_id}")
    public void deleteReview(int review_id);

    // review db에 있는 전체 평균 평점 구하기
    @Select ("select round(avg(star),1) from review")
    public  double getAvgStar();

    // member db에서 성별에 따른 남,녀 성별 개수 count 하기
    @Select("select count(*) from member where gender=#{gender}")
    public int countByGender(int gender);

    // member db에 있는 전체 성별 개수 count 하기
    @Select("select count(*) from member")
    public int getTotalGender();



//    미구현 또는 오류 해결 실패


//    @Update("update review set `like`=#{}")
//    public int updateLike();


//    @Select("select `like` from review where review_id=#{review_id}")
//    public int getLikeCount(int review_id);




}
