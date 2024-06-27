package data.mapper;

import data.dto.AttendanceDto;
import data.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapperInter {

//    @Select("select attendance a. * from attendance a JOIN member m ON a.member_id = m.member_id JOIN course c ON c.name = m.course_name AND c.num = m.num Where m.course_name=${c.name} and m.course_num = ${c.num}")
//    public List<AttendanceDto> AdminShowAttendance();

    // member table에 있는 (게스트, 수강생, 강사) 정보 불러오기
    @Select("select * from member where roles = 'ROLE_GUEST' or roles = 'ROLE_STUDENT' or roles = 'ROLE_TEACHER'")
    public List<MemberDto> getAllMemberList();

    // attendance table 정보 불러오기
    @Select("select * from attendance")
    public List<AttendanceDto> getallattendance();

    // 선택한 권한으로 update
    @Update("update member set roles=#{roles} where member_id=#{member_id}")
    public void updateRole(@Param("roles") String roles, @Param("member_id") int member_id);

    // 선택한 멤버의 권한을 role_deny로 (거절)
    @Update("update member set roles='ROLE_DENY' where member_id=#{member_id}")
    public void roledeny(int member_id);

    // 과정 선택시 해당하는 멤버 전체 리스트 불러오기
    @Select("select * from member where course_name = #{name} and course_num =#{num}")
    public List<MemberDto> selectAllMember(String name, String num);

    // 과정 & 기수 & 권한 검색 시 전체 리스트 불러오기
    @Select("select * from member where course_name = #{name} and course_num =#{num} and roles = #{roles}")
    public List<MemberDto> selectRoleMember(String name, String num, String roles);

    // 권한 검색으로만 전체 리스트 불러오기
    @Select("select * from member where roles = #{roles}")
    public List<MemberDto> selectRole(String roles);
}
