package data.service;

import data.dto.MemberDto;
import data.dto.PortfolioDto;
import data.mapper.MainMapperInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainService {
    private final MainMapperInter mainMapperInter;
    // 메인 페이지 포트폴리오 데이터 가지고오기
    public List<Map<String, Object>> getPortfolioDataMain() {
        System.out.println("mainService 작동중");
        return mainMapperInter.getPortfolioDataMain();
    }
}
