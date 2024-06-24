package data.mapper;

import data.dto.VacationDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VacationMapperInter {
    // 휴가를 신청하면 휴가 데이터가 추가되어야 함
    @Insert("insert into vacation (date, reason, confirm, member_id) values (#{date}, #{reason}, 0, 1)")
    public void insertVacation(VacationDto vacationDto);

    // 휴가를 승인하면 confirm 값이 1로 업데이트 되어야 함
    @Update("update vacation set confirm = 1 where member_id = 1")
    public void updateVacation(int member_id);

    // 휴가 신청했던 것을 취소하면 DB 에서 삭제되어야 함
    @Delete("delete from vacation where member_id = 1")
    public void deleteVacation(int member_id);

    // 휴가 신청한 내역을 조회할 수 있어야 함
    @Select("select * from vacation where member_id = 1")
    public List<VacationDto> getAllVacation(int member_id);
}
