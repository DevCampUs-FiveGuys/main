package data.mapper;

import data.dto.PortfolioDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PortfolioAnswerMapperInter {

    @Insert("""
            insert into portfolio (title,description,created_at)
            values (#{title},#{description},now())
            """)
    public void insertAnswer(PortfolioDto dto);

    @Select("select * from portfolio")
    public List<PortfolioDto> getAnswerData();

}
