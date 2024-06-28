package data.mapper;

import data.dto.PortfolioDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainMapperInter {
    public List<Map<String, Object>> getPortfolioDataMain();
}
