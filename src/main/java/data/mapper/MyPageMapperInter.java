package data.mapper;

import data.dto.MyPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPageMapperInter {
    void insertAttendance(MyPageDto dto);
    void updateAttendance(MyPageDto dto);
    void insertVacation(MyPageDto dto);
    List<MyPageDto> getAttendancesByMemberId(@Param("memberId") int memberId);
    List<MyPageDto> getVacationsByMemberId(@Param("memberId") int memberId);
    void deleteAttendance(@Param("attendanceId") int attendanceId);
    void deleteVacation(@Param("vacationId") int vacationId);
}
