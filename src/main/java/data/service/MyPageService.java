package data.service;

import data.dto.MyPageDto;
import data.mapper.MyPageMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

    @Autowired
    MyPageMapperInter mapper;

    public void saveCheckIn(MyPageDto dto) {
        mapper.insertAttendance(dto);
    }

    public void saveCheckOut(MyPageDto dto) {
        mapper.updateAttendance(dto);
    }

    public void applyVacation(MyPageDto dto) {
        mapper.insertVacation(dto);
    }
}
