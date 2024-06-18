package data.mapper;

import data.dto.MyPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyPageMapperInter {
    void insertAttendance(MyPageDto dto);
    void updateAttendance(MyPageDto dto);
    void insertVacation(MyPageDto dto);
}
