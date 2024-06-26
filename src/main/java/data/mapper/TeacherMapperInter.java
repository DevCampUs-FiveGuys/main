package data.mapper;

import data.dto.AttendanceDto;
import data.dto.MemberDto;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TeacherMapperInter {

    // 학생권한 승인을 위해 권한이 GUEST인 계정 불러오기
    @Select("select * from member where roles='ROLE_GUEST'")
    public List<MemberDto> getStudentList();

    // 출석현황에서 날짜 클릭시 detail페이지에서 출력할 정보들 불러오기
    @Select("select a.*, m.name as membername from attendance a join member m on a.member_id = m.member_id")
    public List<AttendanceDto> getStudentAttendaceList();

    // 학생승인에서 승인버튼 클릭시 해당 member의 권한을 STUDENT로 변경
    @Update("update member set roles='ROLE_STUDENT' where member_id=#{member_id}")
    public void updateStudent(int member_id);

    // 학생승인에서 거절버튼 클릭시 해당 member의 권한을 DENY로 변경
    @Update("update member set roles='ROLE_DENY' where member_id=#{member_id}")
    public void updateGuest(int member_id);
}
