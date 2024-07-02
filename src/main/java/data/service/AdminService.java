package data.service;


import data.dto.AttendanceDto;
import data.dto.MemberDto;
import data.mapper.AdminMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminMapperInter adminMapperInter;

    // 모든 멤버 출력 리스트 (게스트, 학생, 강사)
    public List<MemberDto> getAllMemberList(){
        return adminMapperInter.getAllMemberList();
    }
    // 학생 출결 현황
//    public List<AttendanceDto> getallattendance(){
//        return adminMapperInter.getallattendance();
//    }

    // 선택한 권한으로 권한 update
    public void updateRole(String roles, int member_id){
        adminMapperInter.updateRole(roles, member_id);
    }

    // 거절버튼 : role_deny로 수정
    public void roledeny(int member_id) {
        adminMapperInter.roledeny(member_id);
    }
    // 과정 선택시 해당하는 멤버 전체 리스트 불러오기
    public List<MemberDto> selectAllMember(String name, String num) {
        return adminMapperInter.selectAllMember(name, num);
    }
    // 과정 & 기수 & 권한 검색 시 전체 리스트 불러오기
    public List<MemberDto> selectRoleMember(String name, String num, String roles) {
        return adminMapperInter.selectRoleMember(name, num, roles);
    }
    // 권한 검색으로만 전체 리스트 불러오기
    public List<MemberDto> selectRole(String roles){
        return adminMapperInter.selectRole(roles);
    }

    public List<AttendanceDto> selectallattendance(String name, String num, String dateStr) {
        return adminMapperInter.selectallattendance(name,num, dateStr);
        
    }
    // 출석현황 : 캘린더 클릭 -> 해당 날짜 가져오기
    public List<AttendanceDto> getAttendanceByDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<AttendanceDto> attendanceList = adminMapperInter.getallattendance();

        return attendanceList.stream().filter(dto -> {
            String checkInDate = dateFormat.format(dto.getCheck_in());
            return checkInDate.equals(dateStr);
        }).collect(Collectors.toList());
    }
    //출석현황에서 캘린더 클릭시 클릭을 한 해당 날짜를 가지고 출석인원 count
    public Map<String, Integer> getAttendanceCountsByDate() {
        List<Map<String, Object>> counts = adminMapperInter.getAttendanceCountsByDate();
        Map<String, Integer> attendanceCounts = new HashMap<>();

        for (Map<String, Object> countMap : counts) {
            String date = (String) countMap.get("date");
            Integer count = ((Long) countMap.get("count")).intValue();
            attendanceCounts.put(date, count);
        }
        return attendanceCounts;
    }
    // 병가를 출석으로 출결 업데이트
    public void approveabsent(int member_id, String dateStr) {
        adminMapperInter.approveabsent(member_id, dateStr);
    }





}
