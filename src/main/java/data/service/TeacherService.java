package data.service;

import data.dto.AttendanceDto;
import data.dto.MemberDto;
import data.mapper.TeacherMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapperInter teacherMapperInter;

    // 학생권한 승인을 위해 권한이 GUEST인 계정 불러오기
    public List<MemberDto> getStudentList() {
        return teacherMapperInter.getStudentList();
    }

    // 출석현황에서 날짜 클릭시 detail페이지에서 출력할 정보들 불러오기
    public List<AttendanceDto> getStudentAttendaceList(){
        return teacherMapperInter.getStudentAttendaceList();
    }

    // 학생승인에서 승인버튼 클릭시 해당 member의 권한을 STUDENT로 변경
    public void updateStudent(int member_id) {
        teacherMapperInter.updateStudent(member_id);
    }

    public void updateGuest(int member_id) {
        teacherMapperInter.updateGuest(member_id);
    }

    public List<AttendanceDto> getAttendanceByDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<AttendanceDto> attendanceList = teacherMapperInter.getStudentAttendaceList();

        return attendanceList.stream()
                .filter(dto -> {
                    String checkInDate = dateFormat.format(dto.getCheck_in());
                    return checkInDate.equals(dateStr);
                })
                .collect(Collectors.toList());
    }

}
