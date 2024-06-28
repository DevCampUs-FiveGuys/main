package data.service;

import data.dto.HeartDto;
import data.mapper.HeartMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeartService {

    @Autowired
    private HeartMapperInter heartMapper;

    // 포트폴리오 찜하기 버튼을 누르면 DB 에 저장
    public void insertHeart(int member_id, int portfolio_id) {
        heartMapper.insertHeart(member_id, portfolio_id);
    }

    // 포트폴리오 찜하기 버튼이 눌린 상태에서 또 다시 누르면 DB 에서 삭제
    public void deleteHeart(int member_id, int portfolio_id) {
        heartMapper.deleteHeart(member_id, portfolio_id);
    }

    // 찜한 포트폴리오를 조회 (찜한 포트폴리오가 있는지 확인)
    public List<HeartDto> getHeart(int member_id) {
        return heartMapper.getHeart(member_id);
    }
}
