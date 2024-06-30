package data.mapper;

import data.dto.PortfolioDto;
import data.dto.replyDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PortfolioMapperInter {

    @Insert("insert into portfolio (member_id,title,description,file_name,regroup,restep,relevel,recount,created_at)values (#{member_id},#{title},#{description},#{file_name},#{regroup},#{restep},#{relevel},#{recount},now()) ")
    public void insertPortfolio(PortfolioDto dto);

    @Update("update portfolio set member_id=#{member_id}, title=#{title}, description=#{description}, updated_at=NOW() where portfolio_id=#{portfolio_id}")
    public void updatePortfolio(PortfolioDto dto);

    @Delete("delete from portfolio where portfolio_id=#{portfolio_id}")
    public void deletePortfolio(int portfolio_id);

    @Select("select * from portfolio where portfolio_id=#{portfolio_id}")
    public PortfolioDto getData(int portfolio_id);

    @Select("select count(*) from portfolio")
    public int getTotalCount();

    @Select("select * from portfolio where portfolio_id=#{portfolio_id}")
    public List<PortfolioDto> getPortfolioData(int portfolio_id);

    @Select("select p.*, m.name from portfolio p left join member m on p.member_id = m.member_id order by regroup desc,restep asc, portfolio_id desc limit #{start},#{perpage}")
    public List<PortfolioDto> getPagingList(Map<String, Integer> map);

    @Update("update portfolio set readcount=readcount+1 where portfolio_id=#{portfolio_id}")
    public void updateReadcount(int portfolio_id);

    @Select("SELECT * FROM portfolio p JOIN member m ON m.member_id = p.member_id WHERE p.portfolio_id = #{portfolio_id} order by regroup desc, restep asc limit #{start}, #{perpage}")
    List<PortfolioDto> selectAllRepliesByPortfolio(Map<String, Object> map);

    // 로그인한 회원의 작성한 포트폴리오 리스트 가져오기
    @Select("select * from portfolio where member_id=#{member_id}")
    public List<PortfolioDto> getPortfoliosByMemberId(int member_id);

    @Delete("delete from reply where portfolio_id = #{portfolio_id}")
    public void deleteRepliesByPortfolioId(int portfolio_id);
}
