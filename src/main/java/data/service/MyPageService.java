package data.service;

import data.dto.MyPageDto;
import data.mapper.MyPageMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<MyPageDto> getAttendancesByMemberId(int memberId) {
        return mapper.getAttendancesByMemberId(memberId);
    }

    public List<MyPageDto> getVacationsByMemberId(int memberId) {
        return mapper.getVacationsByMemberId(memberId);
    }

    public void deleteAttendance(int attendanceId) {
        mapper.deleteAttendance(attendanceId);
    }

    public void deleteVacation(int vacationId) {
        mapper.deleteVacation(vacationId);
    }
}
