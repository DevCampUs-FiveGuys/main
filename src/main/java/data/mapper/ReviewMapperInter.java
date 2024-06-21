package data.mapper;

import data.dto.ReviewDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapperInter {

    @Insert("insert into review (content, star, created_at) values (#{content}, #{star}, now())")
    public void insertReview(ReviewDto reviewDto);
//    @Select("select content, created_at, star, `like` like_count,name,gender from review join(select member_id,name,gender from member where course_name=#{name} and course_num=#{num} as mem on mem.member_id = review.member_id)")
//    public List<ReviewDto> selectAllReview(String name, String num);
    @Select("select * from review where review_id=#{review_id}")
    public ReviewDto getReiewData(int review_id);
    @Delete("delete from review where review_id=#{review_id}")
    public void deleteReview(int review_id);
    @Select ("select round(avg(star),1) from review")
    public  double getAvgStar();

    @Select("select * from review order by review_id asc")
    public List<ReviewDto> getAllReview();

//    @Select("select `like` from review where review_id=#{review_id}")
//    public int getLikeCount(int review_id);

    // 성별 개수 count 하기
    @Select("select count(*) from member where gender=#{gender}")
    public int countByGender(int gender);

    @Select("select count(*) from member")
    public int getTotalGender();
//    @Update("update review set `like`=#{}")
//    public int updateLike();

    //해당 후기의 작성자 이름 불러오기
//    @Select("select m.name from member m JOIN review r ON m.member_id = r.member_id JOIN course c ON c.name = m.course_name AND c.num = m.course_num WHERE c.name=#{name} AND c.num = #{num}")
//    public List<String> getMemberName(String name, String num);
}
