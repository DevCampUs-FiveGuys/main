package data.mapper;

import data.dto.ReviewDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapperInter {

    // review db에서 작성한 리뷰 등록
    @Insert("insert into review (content, star, created_at, member_id) values (#{content}, #{star}, now(), #{member_id})")
    public void insertReview(ReviewDto reviewDto);

    // 과정명, 기수 조건 선택 후 보여지는 리뷰 목록들
    @Select("select content, created_at, star, `like`, name, gender, review_id, review.member_id, name from review join (select member_id, name, gender from member where course_name=#{name} and course_num=#{num}) as mem on mem.member_id = review.member_id")
    public List<ReviewDto> selectAllReview(String name, String num);

    // 리뷰 삭제
    @Delete("delete from review where review_id=#{review_id}")
    public void deleteReview(int review_id);

    // 등록된 리뷰 평점구하기
    @Select("select round(avg(star),1) from review")
    public double getAvgStar();

    // 등록된 전체 리뷰 목록들
    @Select("select content, review.created_at, star, `like`, name, gender, review_id, review.member_id from review join member on member.member_id = review.member_id order by review_id desc ")
    public List<ReviewDto> getAllReview();

    // 성별 개수 count 하기
    @Select("select count(*) from member where gender=#{gender} and roles='ROLE_STUDENT'")
    public int countByGender(int gender);

    // 등록된 학생의 전체 수
    @Select("select count(*) from member where roles = 'ROLE_STUDENT'")
    public int getTotalGender();

    // 선택된 과정, 기수의 총 인원
    @Select("select count(*) from member where course_name = #{course_name} and course_num = #{course_num}")
    public int getSelectedTotalCnt();

    // 과정명 기수 조건 설정 후 등록된 리뷰들의 평균 평점
    @Select("select round(avg(star),1) from review join (select * from member where course_name=#{name}and course_num=#{num) as mem on mem.member_id = review.member_id")
    public double getSelectedAvgStar(String name, String num);

    // 좋아요 +1
    @Update("update review SET `like` = `like` +1 where review_id =#{review_id}")
    public void updateLike(long review_id);

    // 좋아요 총 개수
    @Select("SELECT `like` FROM review WHERE review_id = #{review_id}")
    public int getLikeCount(int review_id);

}
