package data.service;

import data.dto.PortfolioDto;
import data.mapper.PortfolioMapperInter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioMapperInter portfolioMapper;

    public void insertPortfolio(PortfolioDto dto)
    {
        portfolioMapper.insertPortfolio(dto);
    }

    public int getTotalCount()
    {
        return portfolioMapper.getTotalCount();
    }

    /*public void updateReadcount(int portfolio_id)
    {
        portfolioMapper.updateReadcount(portfolio_id);
    }*/

    public PortfolioDto getData()
    {
        return portfolioMapper.getData();
    }

    public void updatePortfolio(PortfolioDto dto)
    {
        portfolioMapper.updatePortfolio(dto);
    }

    public void deletePortfolio(int portfolio_id)
    {
        portfolioMapper.deletePortfolio(portfolio_id);
    }

}