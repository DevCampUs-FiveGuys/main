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

    // 입실 데이터 추가
    public void insertCheckIn(String check_in, int member_id) {
        attendanceMapper.insertCheckIn(check_in, member_id);
    }

    // 퇴실 데이터 추가 (업데이트)
    public void updateCheckOut(String check_out, int member_id) {
        attendanceMapper.updateCheckOut(check_out, member_id);
    }

    // 전체 출석 데이터 조회
    public List<AttendanceDto> getAllAttendance(int member_id) {
        return attendanceMapper.getAllAttendance(member_id);
    }

    // 입실 데이터 삭제
    public void deleteCheckIn(int member_id) {
        attendanceMapper.deleteCheckIn(member_id);
    }

    // 퇴실 데이터 삭제 (업데이트)
    public void deleteCheckOut(int member_id) {
        attendanceMapper.deleteCheckOut(member_id);
    }

    // 출석일수 조회
    public int getAttendanceDays(int member_id) {
        return attendanceMapper.getAttendanceDays(member_id);
    }

    // 지각 데이터 업데이트
    public void updateLate(int member_id) {
        attendanceMapper.updateLate(member_id);
    }

    // 지각일수 최댓값 조회
    public int getLateDays(int member_id) {
        return attendanceMapper.getLateDays(member_id);
    }

    // 결석 데이터 업데이트
    public void updateAbsent(int member_id) {
        attendanceMapper.updateAbsent(member_id);
    }

    // 결석일수 최댓값 조회
    public int getAbsentDays(int member_id) {
        return attendanceMapper.getAbsentDays(member_id);
    }

    // 휴가 데이터 추가
    public void insertVacation(AttendanceDto attendanceDto) {
        attendanceMapper.insertVacation(attendanceDto);
    }

    // 병가 데이터 업데이트
    public void updateHospital(int member_id, int confirm) {
        attendanceMapper.updateHospital(member_id, confirm);
    }
}
