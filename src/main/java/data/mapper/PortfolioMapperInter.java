package data.mapper;

import data.dto.PortfolioDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PortfolioMapperInter {

    public void insertPortfolio(PortfolioDto dto);

    public void updatePortfolio(PortfolioDto dto);

    public void deletePortfolio(int portfolio_id);

    public PortfolioDto portfolioData(int Portfolio_id);
}

