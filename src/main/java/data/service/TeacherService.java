package data.service;

import data.dto.AttendanceDto;
import data.dto.MemberDto;
import data.mapper.TeacherMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void updateTeacher(int member_id) {
        teacherMapperInter.updateTeacher(member_id);
    }

}
