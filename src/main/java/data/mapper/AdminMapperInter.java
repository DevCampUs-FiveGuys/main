package data.mapper;

import data.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapperInter {

//    @Select("select attendance a. * from attendance a JOIN member m ON a.member_id = m.member_id JOIN course c ON c.name = m.course_name AND c.num = m.num Where m.course_name=${c.name} and m.course_num = ${c.num}")
//    public List<AttendanceDto> AdminShowAttendance();

    @Select("select * from member where roles = 'ROLE_GUEST' or roles = 'ROLE_STUDENT' or roles = 'ROLE_TEACHER'")
    public List<MemberDto> getAllMemberList();
}
