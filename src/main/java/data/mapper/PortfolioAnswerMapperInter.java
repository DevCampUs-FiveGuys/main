package data.mapper;

import data.dto.PortfolioDto;
import data.dto.replyDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PortfolioAnswerMapperInter {

    @Insert("insert into reply (member_id,portfolio_id,comment,created_at) values (#{member_id},#{portfolio_id},#{comment},now())")
    public void insertAnswer(replyDto dto);

    @Select("select * from reply where portfolio_id=#{portfolio_id} order by portfolio_id desc")
    public List<replyDto> getAnswerData(int portfolio_id);

    @Delete("delete from reply where reply_id=#{reply_id}")
    public void deleteAnswer(int reply_id);

    @Select("SELECT r.member_id, r.portfolio_id, r.comment, m.name, r.created_at, r.reply_id " +
            "FROM reply r " +
            "JOIN member m ON m.member_id = r.member_id " +
            "WHERE r.portfolio_id = #{portfolio_id}")
    List<replyDto> selectAllRepliesByPortfolioId(@Param("portfolio_id") int portfolio_id);

}