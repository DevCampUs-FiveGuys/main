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


    public List<MemberDto> getStudentList() {
        return teacherMapperInter.getStudentList();
    }

    public List<AttendanceDto> getStudentAttendaceList(){
        return teacherMapperInter.getStudentAttendaceList();
    }

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
