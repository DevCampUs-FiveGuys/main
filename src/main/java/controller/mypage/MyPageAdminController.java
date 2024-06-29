package controller.mypage;

import data.dto.AttendanceDto;
import data.dto.CourseDto;
import data.dto.MemberDto;
import data.dto.ReviewDto;
import data.service.AdminService;
import data.service.AttendanceService;
import data.service.MemberService;
import data.service.ReviewService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/mypage")
@RequiredArgsConstructor
public class MyPageAdminController {

    @NonNull
    private AdminService adminService;
    @NonNull
    private ReviewService reviewService;
    @NonNull
    private MemberService memberService;


    // 출석현황 : 캘린더
    @GetMapping("")
    public String attendanceList(Model model) {

        model.addAttribute("page", "Admattendancelist");


        return "thymeleaf/admin/Admattendancelist";
    }

    // 출석현황 : 캘린더 클릭 시 출석 attendance detail로 이동
    @GetMapping("/attendancedetail")
    public String attendancedetail(
            @RequestParam("date") String dateStr,
            Model model) {

        List<AttendanceDto> attendancelist = adminService.getAttendanceByDate(dateStr);

        List<CourseDto> courselist = reviewService.getAllCourseList();


        model.addAttribute("page", "AttendanceDetail");
        model.addAttribute("attendancelist", attendancelist);
        model.addAttribute("courselist",courselist);
        model.addAttribute("selectedDate",dateStr);

        return "thymeleaf/admin/AttendanceDetail";
    }

    //출석현황 : 캘린더 클릭시 클릭을 한 해당 날짜를 가지고 출석인원 count(Map)
    @GetMapping("/attendanceCounts")
    @ResponseBody
    public Map<String, Integer> getAttendanceCounts() {
        return adminService.getAttendanceCountsByDate();
    }

    // 권한수정
    @GetMapping("/changerole")
    public String changeRoles(Model model) {

        List<MemberDto> memberlist = adminService.getAllMemberList();
        List<CourseDto> courselist = reviewService.getAllCourseList();

        model.addAttribute("memberlist", memberlist);
        model.addAttribute("courselist", courselist);
        model.addAttribute("page", "Admchangeroles");

        return "thymeleaf/admin/Admchangeroles";
    }

    // 권한수정 : 권한 update 승인
    @PostMapping("/updateroles")
    public String updateRoles(@RequestParam("roles") String roles, @RequestParam("member_id") int member_id) {
        System.out.println(roles);
        System.out.println(member_id);

        adminService.updateRole(roles, member_id);

        System.out.println(roles);
        System.out.println(member_id);
        return "redirect:/admin/mypage/changerole";
    }

    // 권한수정 : 거절 버튼 (ROLE_DENT)
    @PostMapping("/roledeny")
    public String roledeny(
            @RequestParam int member_id
    ) {
        adminService.roledeny(member_id);

        return "redirect:/admin/mypage/changerole";
    }

    // 과정명 선택 -> 해당하는 기수명
    @GetMapping("/list/names")
    @ResponseBody
    public List<String> getCourseNums(@RequestParam("name") String name) {
        return reviewService.getNumOfCourse(name);
    }

    // 기수명 선택 -> 해당 맴버 출력
    @GetMapping("/list/nums")
    @ResponseBody
    public List<MemberDto> selectAllMember(@RequestParam("name") String name, @RequestParam("num") String num, @RequestParam(required = false) String roles, Model model) {

        if ("ALL".equals(roles)) {
            // roles가 null이면 모든 역할을 포함하는 로직
            return adminService.selectAllMember(name, num); // 모든 역할 포함
        } else {
            // 특정 역할만 포함하는 로직
            return adminService.selectRoleMember(name, num, roles); // 모든 역할 포함
        }
    }

    // 권한 : '전체' 추가
    @GetMapping("/list/role")
    @ResponseBody
    public List<MemberDto> selectRole(@RequestParam(required = false) String roles) {
        if ("ALL".equals(roles)) {
            // roles가 null이면 모든 역할을 포함하는 로직
            return adminService.getAllMemberList(); // 모든 역할 포함
        }
        return adminService.selectRole(roles);
    }

    // 현재 멤버의 프로필 정보
    @GetMapping("/updateprofile")
    public String updateProfile(Authentication authentication, Model model) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            model.addAttribute("member", member);
            model.addAttribute("page", "UpdateProfile");
        }
        return "thymeleaf/admin/UpdateProfile";
    }

    // 프로필 수정 후 업데이트
    @PostMapping("/update")
    public String updateInfo(@ModelAttribute MemberDto memberDto, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            member.setTel(memberDto.getTel());
            member.setPhoto(memberDto.getPhoto());
            memberService.updateMember(member);
        }
        return "redirect:/admin/mypage/updateprofile";
    }

    // 기수명 선택 -> 해당 멤버 출석 목록
    @GetMapping("/list/nums/attendance")
    @ResponseBody
    public List<AttendanceDto> selectAllMember(@RequestParam("name") String name, @RequestParam("num") String num, @RequestParam("dateStr") String dateStr) {

        List<AttendanceDto> selectedAttendance = adminService.selectallattendance(name, num, dateStr);

        return selectedAttendance;
    }

    // 병가를 출석으로 출결 업데이트
    @GetMapping("/attendancedetail/update")
    @ResponseBody
    public String approveabsent(@RequestParam("member_id") int member_id, @RequestParam("dateStr")String dateStr){

        adminService.approveabsent(member_id, dateStr);

        return "redirect:/admin/mypage/attendancedetail?date={dateStr}";
    }

    // 훈련 추가 페이지
    @GetMapping("/CreateCourse")
    public String createcourse(Model model) {

        List<CourseDto> courseinfo = reviewService.getCourseInfo();

        CourseDto courseDto = new CourseDto();

        model.addAttribute("courseDto",courseDto);
        model.addAttribute("courseinfo", courseinfo);
        model.addAttribute("page", "CreateCourse");

        return "thymeleaf/admin/CreateCourse";
    }

    @PostMapping("/course/insert")
    public String courseinsert(@ModelAttribute CourseDto courseDto) {

        reviewService.insertCourse(courseDto);

        return "redirect:/admin/mypage/CreateCourse";
    }
}

