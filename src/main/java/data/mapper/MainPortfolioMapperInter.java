package data.mapper;

import data.dto.mainPortfolioDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapperInter {
    public List<mainPortfolioDto> getPortfolioDataMain();
}
