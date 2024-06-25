package data.mapper;

import data.dto.AttendanceDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttendanceMapperInter {
    // 로그인 후 출석현황 페이지에서 입실 버튼을 누르면 입실 (check_in) 시간을 DB 에 저장
    @Insert("insert into attendance (check_in, member_id) values (now(), #{member_id})")
    public void insertCheckIn(String check_in, int member_id);

    // 입실 후 퇴실 버튼을 누르면 퇴실 (check_out) 시간을 DB 에 저장
    @Update("update attendance set check_out = now() where DATE(check_in) = DATE(now()) and member_id = #{member_id}")
    public void updateCheckOut(String check_out, int member_id);

    // 출석현황 페이지 전체 조회
    @Select("select * from attendance where member_id = #{member_id}")
    public List<AttendanceDto> getAllAttendance(int member_id);

    // 캘린더 내에서 입실 마크를 클릭해서 삭제하면 check_in 데이터도 DB 에서 삭제
    @Delete("delete from attendance where DATE(check_in) = DATE(now()) and member_id = #{member_id}")
    public void deleteCheckIn(int member_id);

    // 당일에 출석을 하면 출석일수 데이터가 업데이트 되어야함 (출석일수 조회)
    @Select("select count(*) from attendance where member_id = #{member_id} and check_in is not null and check_out is not null and absent = 0 and vacation = 0 and hospital = 0")
    public int getAttendanceDays(int member_id);

    // 당일에 지각을 하면 지각 데이터가 업데이트 되어야함
    @Update("update attendance set late = late + 1 where DATE(check_in) = DATE(now()) and member_id = #{member_id}")
    public void updateLate(int member_id);

    // 지각일수 조회
    @Select("select count(late) from attendance where late = 1")
    public int getLateDays(int member_id);

    // 당일에 결석을 하면 결석 데이터가 업데이트 되어야함
    @Update("update attendance set absent = absent + 1 where DATE(check_in) = DATE(now()) and member_id = #{member_id}")
    public void updateAbsent(int member_id);

    // 결석일수 조회
    @Select("select count(absent) from attendance where absent = 1")
    public int getAbsentDays(int member_id);

    // 지각일수에 따른 결석 업데이트 (지각 3회 당 결석 1회)
    @Update("update attendance set absent = absent + (late / 3) where member_id = #{member_id}")
    public void updateAbsentBasedOnLate(int member_id);

    // 휴가를 신청하면 휴가 데이터가 추가되어야 함
    @Insert("insert into attendance values (null, null, null, default, +1, default, default, #{member_id})")
    public void insertVacation(AttendanceDto attendanceDto);

    // 병가를 신청하면 병가 데이터가 업데이트 되어야함
    @Update("update attendance set hospital = hospital + 1, absent = absent - 1 where DATE(check_in) = DATE(now()) and member_id = #{member_id} and #{confirm} = 1")
    public void updateHospital(int member_id, int confirm);
}
