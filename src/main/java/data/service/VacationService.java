package data.service;

import data.dto.VacationDto;
import data.mapper.VacationMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationService {

    @Autowired
    private VacationMapperInter vacationMapper;

    // 휴가를 신청하는 메소드
    public void insertVacation(VacationDto vacationDto) {
        vacationMapper.insertVacation(vacationDto);
    }

    // 휴가를 승인하는 메소드
    public void updateVacation(int member_id) {
        vacationMapper.updateVacation(member_id);
    }

    // 휴가 신청을 취소하는 메소드
    public void deleteVacation(int member_id) {
        vacationMapper.deleteVacation(member_id);
    }

    // 모든 휴가 신청 내역을 조회하는 메소드
    public List<VacationDto> getAllVacation(int member_id) {
        return vacationMapper.getAllVacation(member_id);
    }

    // 모든 승인된 휴가 조회
    public List<VacationDto> getApprovedVacations(int member_id) {
        return vacationMapper.getApprovedVacations(member_id);
    }

    //휴가 승인 안된 휴가 신청 내역들 조회
    public List<VacationDto> selectAllVacation() {
        return vacationMapper.selectAllVacation();
    }

    public void approveVacation(int vacation_id) {
        vacationMapper.approveVacation(vacation_id);
    }


    public void denyVacation(int vacation_id) {
        vacationMapper.denyVacation(vacation_id);
    }
}
