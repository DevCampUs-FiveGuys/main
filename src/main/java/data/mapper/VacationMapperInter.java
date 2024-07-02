package data.mapper;

import data.dto.VacationDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VacationMapperInter {
    // 휴가를 신청하면 휴가 데이터가 추가되어야 함
    @Insert("insert into vacation (date, reason, confirm, member_id) values (#{date}, #{reason}, 0, #{member_id})")
    public void insertVacation(VacationDto vacationDto);

    // 휴가를 승인하면 confirm 값이 1로 업데이트 되어야 함
    @Update("update vacation set confirm = 1 where member_id = #{member_id}")
    public void updateVacation(int member_id);

    // 휴가 신청했던 것을 취소하면 DB 에서 삭제되어야 함
    @Delete("delete from vacation where member_id = #{member_id}")
    public void deleteVacation(int member_id);

    // 휴가 신청한 내역을 조회할 수 있어야 함
    @Select("select * from vacation where member_id = #{member_id}")
    public List<VacationDto> getAllVacation(int member_id);

    // 모든 승인된 휴가 조회
    @Select("select * from vacation where member_id = #{member_id} and confirm = 1")
    public List<VacationDto> getApprovedVacations(int member_id);

    @Select("update vacation set confirm = 1 where vacation_id = #{vacation_id}")
    public void approveVacation(int vacation_id);

    @Select("update vacation set confirm = 2 where vacation_id = #{vacation_id}")
    public void denyVacation(int vacation_id);

    //휴가 승인 안된 휴가 신청 내역들 조회
    @Select("select v.*, m.name  from vacation v join member m on v.member_id = m.member_id")
    public List<VacationDto> selectAllVacation();

    //휴가 승인 또는 신청한 학생 조회
    @Select("select v.*, m.name  from vacation v join member m on v.member_id = m.member_id where confirm=0 or confirm=1")
    public List<VacationDto> getAllconfirm();
}
