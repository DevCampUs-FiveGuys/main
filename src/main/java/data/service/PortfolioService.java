package data.service;

import data.dto.PortfolioDto;
import data.mapper.PortfolioMapperInter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioMapperInter portfolioMapper;

    public void insertPortfolio(PortfolioDto dto)
    {
        portfolioMapper.insertPortfolio(dto);
    }

    public List<PortfolioDto> getPortfolioData(int portfolio_id)
    {
        return portfolioMapper.getPortfolioData(portfolio_id);
    }

    public int getTotalCount()
    {
        return portfolioMapper.getTotalCount();
    }

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

    public List<PortfolioDto> getPagingList(int start, int perpage)
    {
        Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("perpage", perpage);

        return portfolioMapper.getPagingList(map);
    }

    public void updateReadcount(int portfolio_id)
    {
        portfolioMapper.updateReadcount(portfolio_id);
    }

}