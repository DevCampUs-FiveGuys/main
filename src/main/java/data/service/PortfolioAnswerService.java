package data.service;

import data.dto.PortfolioDto;
import data.dto.replyDto;
import data.mapper.PortfolioAnswerMapperInter;
import data.mapper.PortfolioMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioAnswerService {

    @Autowired
    private PortfolioAnswerMapperInter anInter;
    @Autowired
    private PortfolioAnswerMapperInter portfolioAnswerMapperInter;

    public void insertAnswer(replyDto dto)
    {
        anInter.insertAnswer(dto);
    }

    public List<replyDto> getAnswerData(int reply_id)
    {
        return anInter.getAnswerData(reply_id);
    }

    public void deleteAnswer(int reply_id)
    {
        anInter.deleteAnswer(reply_id);
    }

    public List<replyDto> getAllRepliesByPortfolioId(int portfolio_id) {
        return portfolioAnswerMapperInter.selectAllRepliesByPortfolioId(portfolio_id);
    }

}