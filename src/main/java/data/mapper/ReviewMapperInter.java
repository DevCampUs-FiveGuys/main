package data.mapper;

import data.dto.ReviewDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapperInter {

    // review db에서 작성한 후기 등록
    @Insert("insert into review (content, star, created_at, member_id) values (#{content}, #{star}, now(), #{member_id})")
    public void insertReview(ReviewDto reviewDto);

    @Select("select content, created_at, star, `like`, name, gender, review_id, review.member_id, name from review join (select member_id, name, gender from member where course_name=#{name} and course_num=#{num}) as mem on mem.member_id = review.member_id")
    public List<ReviewDto> selectAllReview(String name, String num);

    @Select("select * from review where review_id=#{review_id}")
    public ReviewDto getReiewData(int review_id);

    @Delete("delete from review where review_id=#{review_id}")
    public void deleteReview(int review_id);

    @Select("select round(avg(star),1) from review")
    public double getAvgStar();

    @Select("select content, created_at, star, `like`, name, gender, review_id, review.member_id from review join member on member.member_id = review.member_id order by review_id asc")
    public List<ReviewDto> getAllReview();

    // 성별 개수 count 하기
    @Select("select count(*) from member where gender=#{gender}")
    public int countByGender(int gender);


    @Select("select count(*) from member")
    public int getTotalGender();

    // 선택된 과정, 기수의 총 인원
    @Select("select count(*) from member where course_name = #{course_name} and course_num = #{course_num}")
    public int getSelectedTotalCnt();

    @Select("select round(avg(star),1) from review join (select * from member where course_name=#{name}and course_num=#{num) as mem on mem.member_id = review.member_id")
    public double getSelectedAvgStar(String name, String num);

    // 좋아요 +1
    @Update("update review SET `like` = `like` +1 where review_id =#{review_id}")
    public void updateLike(long review_id);

    @Select("SELECT `like` FROM review WHERE review_id = #{review_id}")
    public int getLikeCount(int review_id);

    //해당 후기의 작성자 이름 불러오기
//    @Select("select m.name from member m JOIN review r ON m.member_id = r.member_id JOIN course c ON c.name = m.course_name AND c.num = m.course_num WHERE c.name=#{name} AND c.num = #{num}")
//    public List<String> getMemberName(String name, String num);
}
