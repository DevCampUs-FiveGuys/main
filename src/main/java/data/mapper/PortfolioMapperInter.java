package data.mapper;

import data.dto.PortfolioDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PortfolioMapperInter {

    @Insert("""
            insert into portfolio (title,description,file_name,created_at)
            values (#{title},#{description},#{file_name},now())
            """)
    public void insertPortfolio(PortfolioDto dto);

    @Update("update portfolio set title=#{title},description=#{description},updated_at=NOW() where portfolio_id=#{portfolio_id}")
    public void updatePortfolio(PortfolioDto dto);

    @Delete("delete from portfolio where portfolio_id=#{portfolio_id}")
    public void deletePortfolio(int portfolio_id);

    @Select("select * from portfolio")
    public PortfolioDto getData();

    @Select("select count(*) from portfolio")
    public int getTotalCount();

    @Select("select * from portfolio")
    public List<PortfolioDto> getPorfolioData();


    @Select("select * from portfolio order by regroup desc,restep asc limit #{start},#{perpage}")
    public List<PortfolioDto> getPagingList(Map<String, Integer> map);
}