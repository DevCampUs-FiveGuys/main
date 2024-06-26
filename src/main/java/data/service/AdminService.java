package data.service;


import data.dto.AttendanceDto;
import data.dto.MemberDto;
import data.mapper.AdminMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapperInter adminMapperInter;

    // 모든 멤버 출력 리스트 (게스트, 학생, 강사)
    public List<MemberDto> getAllMemberList(){
        return adminMapperInter.getAllMemberList();
    }
    // 학생 출결 현황
    public List<AttendanceDto> getallattendance(){
        return adminMapperInter.getallattendance();
    }



}
