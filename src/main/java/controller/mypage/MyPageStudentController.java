package controller.mypage;

import data.dto.AttendanceDto;
import data.dto.HeartDto;
import data.dto.MemberDto;
import data.dto.VacationDto;
import data.service.AttendanceService;
import data.service.HeartService;
import data.service.MemberService;
import data.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/student/mypage")
public class MyPageStudentController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private VacationService vacationService;

    @Autowired
    private HeartService heartService;

    // 출석현황 페이지로 이동
    @ModelAttribute
    @GetMapping("/attendance")
    public String attendance(Authentication authentication, Model model) {
        if (authentication != null) {
            String email = authentication.getName(); // 인증된 사용자의 이메일
            int member_id = memberService.findByUsername(email).getMember_id(); // 인증된 사용자의 이메일이 가지고 있는 member_id
            String userName = memberService.findByUsername(email).getName(); // 인증된 사용자의 이름
            model.addAttribute("member_id", member_id);
            model.addAttribute("userName", userName);
            model.addAttribute("page", "attendance");
        }
        return "thymeleaf/student/attendance";
    }

    // 찜목록 페이지로 이동
    @GetMapping("/portfolio_favorites")
    public String portfolio_favorites(Authentication authentication, Model model) {
        if (authentication != null) {
            String email = authentication.getName(); // 인증된 사용자의 이메일
            int member_id = memberService.findByUsername(email).getMember_id(); // 인증된 사용자의 이메일이 가지고 있는 member_id
            List<HeartDto> heartList = heartService.getHeart(member_id); // 찜한 포트폴리오 목록
            model.addAttribute("heartList", heartList);
            model.addAttribute("member_id", member_id);
            model.addAttribute("page", "favorites");
        }
        return "thymeleaf/student/portfolio_favorites";
    }

    // 게시글 페이지로 이동
    @GetMapping("/portfolio_posts")
    public String portfolio_posts(Model model) {
        model.addAttribute("page", "posts");
        return "thymeleaf/student/portfolio_posts";
    }

    // 정보수정 페이지로 이동
    @GetMapping("/updateProfile")
    public String updateProfile(Authentication authentication, Model model) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            model.addAttribute("member", member);
            model.addAttribute("page", "updateProfile");
        }
        return "thymeleaf/student/updateProfile";
    }

    // 정보수정 업데이트
    @PostMapping("/updateProfile")
    public String updateInfo(@ModelAttribute MemberDto memberDto, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            member.setTel(memberDto.getTel());
            memberService.updateMember(member);
        }
        return "redirect:/student/mypage/updateProfile";
    }

    // 입실 데이터 추가
    @PostMapping("/attendance/checkin")
    @ResponseBody
    public void insertCheckIn(@RequestParam String check_in, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            attendanceService.insertCheckIn(check_in, member_id);
        }
    }

    // 퇴실 데이터 추가 (업데이트)
    @PostMapping("/attendance/checkout")
    @ResponseBody
    public void updateCheckOut(@RequestParam String check_out, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            attendanceService.updateCheckOut(check_out, member_id);
        }
    }

    // 전체 출석 데이터 조회
    @GetMapping("/attendance/all")
    @ResponseBody
    public List<AttendanceDto> getAllAttendance(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            return attendanceService.getAllAttendance(member_id);
        }
        return null;
    }

    // 입실 데이터 삭제
    @DeleteMapping("/attendance/checkin")
    @ResponseBody
    public void deleteCheckIn(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            attendanceService.deleteCheckIn(member_id);
        }
    }

    // 출석일수 조회
    @GetMapping("/attendance/days")
    @ResponseBody
    public int getAttendanceDays(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            return attendanceService.getAttendanceDays(member_id);
        }
        return 0;
    }

    // 지각 데이터 업데이트
    @PostMapping("/attendance/late")
    @ResponseBody
    public void updateLate(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            attendanceService.updateLate(member_id);
        }
    }

    // 지각일수 최댓값 조회
    @GetMapping("/attendance/late/max")
    @ResponseBody
    public int getLateDays(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            return attendanceService.getLateDays(member_id);
        }
        return 0;
    }

    // 결석 데이터 업데이트
    @PostMapping("/attendance/absent")
    @ResponseBody
    public void updateAbsent(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            attendanceService.updateAbsent(member_id);
        }
    }

    // 지각을 결석으로 업데이트
    @PostMapping("/attendance/updateAbsentBasedOnLate")
    @ResponseBody
    public void updateAbsentBasedOnLate(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            attendanceService.updateAbsentBasedOnLate(member_id);
        }
    }

    // 결석일수 최댓값 조회
    @GetMapping("/attendance/absent/max")
    @ResponseBody
    public int getAbsentDays(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            return attendanceService.getAbsentDays(member_id);
        }
        return 0;
    }

    // 휴가 데이터 추가
    @PostMapping("/attendance/vacation")
    @ResponseBody
    public void insertVacation(@RequestBody AttendanceDto attendanceDto, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            attendanceDto.setMember_id(member_id);
            attendanceService.insertVacation(attendanceDto);
        }
    }

    // 병가 데이터 업데이트
    @PostMapping("/attendance/hospital")
    @ResponseBody
    public void updateHospital(@RequestParam int confirm, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            attendanceService.updateHospital(member_id, confirm);
        }
    }

    // 휴가 신청
    @PostMapping("/vacation/apply")
    @ResponseBody
    public void insertVacation(@RequestParam String date, @RequestParam String reason, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            VacationDto vacationDto = VacationDto.builder()
                    .date(Timestamp.valueOf(date + " 00:00:00"))  // Assuming the date is in YYYY-MM-DD format
                    .reason(reason)
                    .member_id(member_id)
                    .build();
            vacationService.insertVacation(vacationDto);
        }
    }

    // 휴가 승인
    @PostMapping("/vacation/approve")
    @ResponseBody
    public void updateVacation(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            vacationService.updateVacation(member_id);
        }
    }

    // 휴가 취소
    @DeleteMapping("/vacation/cancel")
    @ResponseBody
    public void deleteVacation(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            vacationService.deleteVacation(member_id);
        }
    }

    // 모든 휴가 조회
    @GetMapping("/vacation/all")
    @ResponseBody
    public List<VacationDto> getAllVacation(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            int member_id = memberService.findByUsername(email).getMember_id();
            return vacationService.getAllVacation(member_id);
        }
        return null;
    }
}
