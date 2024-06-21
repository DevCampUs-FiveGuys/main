package data.service;

import data.dto.PortfolioDto;
import data.mapper.PortfolioAnswerMapperInter;
import data.mapper.PortfolioMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioAnswerService {

    @Autowired
    private PortfolioAnswerMapperInter anInter;

    public void insertAnswer(PortfolioDto dto)
    {
        anInter.insertAnswer(dto);
    }

    public List<PortfolioDto> getAnswerData()
    {
        return anInter.getAnswerData();
    }
}
