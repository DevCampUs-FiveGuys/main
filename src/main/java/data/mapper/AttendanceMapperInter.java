package data.mapper;

import data.dto.AttendanceDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttendanceMapperInter {

    // 출석 기록을 추가하는 쿼리
    @Insert("INSERT INTO attendance (check_in, check_out, absent, vacation, hospital, late, member_id) " +
            "VALUES (#{check_in}, #{check_out}, #{absent}, #{vacation}, #{hospital}, #{late}, #{member_id})")
    @Options(useGeneratedKeys = true, keyProperty = "attendance_id")
    public void insertAttendance(AttendanceDto attendance);

    // 출석 기록을 수정하는 쿼리
    @Update("UPDATE attendance SET check_in = #{check_in}, check_out = #{check_out}, absent = #{absent}, " +
            "vacation = #{vacation}, hospital = #{hospital}, late = #{late} WHERE attendance_id = #{attendance_id}")
    public void updateAttendance(AttendanceDto attendance);

    // 출석 기록을 삭제하는 쿼리
    @Delete("DELETE FROM attendance WHERE attendance_id = #{attendance_id}")
    public void deleteAttendance(@Param("attendance_id") int attendance_id);

    // 출석 기록을 조회하는 쿼리
    @Select("SELECT * FROM attendance WHERE member_id = #{member_id}")
    List<AttendanceDto> selectAttendanceByMemberId(@Param("member_id") int member_id);
}
