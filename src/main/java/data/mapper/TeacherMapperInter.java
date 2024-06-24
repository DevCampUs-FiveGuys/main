package data.mapper;

import data.dto.AttendanceDto;
import data.dto.MemberDto;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TeacherMapperInter {


    @Select("select * from member where roles='ROLE_GUEST'")
    public List<MemberDto> getStudentList();

    @Select("select * from attendance")
    public List<AttendanceDto> getStudentAttendaceList();

    @Update("update member set roles='ROLE_STUDENT' where member_id=#{member_id}")
    public void updateStudent(int member_id);

    @Update("update member set roles='ROLE_GUEST' where member_id=#{member_id}")
    public void updateGuest(int member_id);
}
