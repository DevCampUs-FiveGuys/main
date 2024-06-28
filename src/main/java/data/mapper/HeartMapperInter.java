package data.mapper;

import data.dto.HeartDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HeartMapperInter {
    // 포트폴리오 찜하기 버튼을 누르면 DB 에 저장
    @Insert("INSERT INTO heart (member_id, portfolio_id) VALUES (#{member_id}, #{portfolio_id})")
    public void insertHeart(int member_id, int portfolio_id);

    // 포트폴리오 찜하기 버튼이 눌린 상태에서 또 다시 누르면 DB 에서 삭제
    @Delete("DELETE FROM heart WHERE member_id = #{member_id} AND portfolio_id = #{portfolio_id}")
    public void deleteHeart(int member_id, int portfolio_id);

    // 찜한 포트폴리오를 조회 (찜한 포트폴리오가 있는지 확인)
    @Select("SELECT * FROM heart WHERE member_id = #{member_id}")
    public List<HeartDto> getHeart(int member_id);
}
