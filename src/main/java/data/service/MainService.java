package data.service;

import data.dto.mainPortfolioDto;
import data.dto.mainReviewDto;
import data.mapper.MainPortfolioMapperInter;
import data.mapper.MainReviewMapperInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final MainPortfolioMapperInter mainPortfolioMapperInter;
    private final MainReviewMapperInter mainReviewMapperInter;
    // 메인 페이지 포트폴리오 데이터 가지고오기
    public List<mainPortfolioDto> getPortfolioDataMain() {
        List<mainPortfolioDto> mainPortfolioList = mainPortfolioMapperInter.getPortfolioDataMain();
        // 만약 list 의 사이즈가 8개보다 많을 경우 섞은 다음에 0번 부터 7번까지 출력
        if (mainPortfolioList.size() >= 8){
            Collections.shuffle(mainPortfolioList);
            return mainPortfolioList.subList(0, 8);
        }
        // 그렇지 않을 경우에는 섞기만 하여 return
        Collections.shuffle(mainPortfolioList);
        return mainPortfolioList;
    }

    public List<mainReviewDto> getReviewDataMain() {
        List<mainReviewDto> mainReviewList = mainReviewMapperInter.getReviewDataMain();
        Collections.shuffle(mainReviewList);
        return mainReviewList.subList(0,3);
    }
}
