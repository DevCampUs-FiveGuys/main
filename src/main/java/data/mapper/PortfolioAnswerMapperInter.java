package data.mapper;

import data.dto.PortfolioDto;
import data.dto.replyDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PortfolioAnswerMapperInter {

    @Insert("""
            insert into reply (reply_id,member_id,portfolio_id,comment,email,name,num,regroup,restep,relevel,recount,created_at)
            values (#{reply_id},#{member_id},#{portfolio_id},#{comment},#{email},#{name},#{num},#{regroup},#{restep},#{relevel},#{recount},now())
            """)
    public void insertAnswer(replyDto dto);

    @Select("select * from reply where reply_id=#{reply_id} order by reply_id desc")
    public List<replyDto> getAnswerData(int reply_id);

    @Delete("delete from reply where reply_id=#{reply_id}")
    public void deleteAnswer(int reply_id);

}