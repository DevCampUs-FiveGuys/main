package data.service;

import data.dto.AttendanceDto;
import data.mapper.AttendanceMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceMapperInter attendanceMapper;

    // 출석 기록을 추가하는 메소드 (입실, 퇴실, 결석, 휴가, 병가, 지각)
    public void insertAttendance(AttendanceDto attendance) {
        attendanceMapper.insertAttendance(attendance);
    }

    // 출석 기록을 수정하는 메소드
    public void updateAttendance(AttendanceDto attendance) {
        attendanceMapper.updateAttendance(attendance);
    }

    // 출석 기록을 삭제하는 메소드 (출석 ID로)
    public void deleteAttendance(int attendance_id) {
        attendanceMapper.deleteAttendance(attendance_id);
    }

    // 출석 기록을 조회하는 메소드 (회원 ID로)
    public List<AttendanceDto> getAttendanceByMemberId(int member_id) {
        return attendanceMapper.selectAttendanceByMemberId(member_id);
    }
}
