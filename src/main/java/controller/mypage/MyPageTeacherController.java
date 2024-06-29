package controller.mypage;

import data.dto.AttendanceDto;
import data.dto.MemberDto;
import data.naver.cloud.NcpObjectStorageService;
import data.service.AttendanceService;
import data.service.MemberService;
import data.service.TeacherService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import data.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher/mypage")
@RequiredArgsConstructor
public class MyPageTeacherController {

    @NonNull
    private TeacherService teacherService;

    @NonNull
    private MemberService memberService;

    private String bucketName="bitcamp-bucket-149";
    private String folderName="semiproject";


    @Autowired
    private NcpObjectStorageService storageService;

    //출석현황 페이지로 이동
    @GetMapping("")
    public String attendancelist(Model model) {

        model.addAttribute("page", "attendanceList");

        return "thymeleaf/teacher/attendanceList";
    }

    //출석현황에서 캘린더 클릭 시 출석 인원확인 페이지로 이동
    @GetMapping("/attendancedetail")
    public String attendancedetail(
            @RequestParam("date") String dateStr,
            Model model
    ) {
        List<AttendanceDto> attendancelist = teacherService.getAttendanceByDate(dateStr);

        model.addAttribute("page", "attendanceDetail");

        model.addAttribute("attendancelist",attendancelist);
        return "thymeleaf/teacher/attendanceDetail";
    }

    //학생승인 화면으로 이동
    @GetMapping("/roleapprove")
    public String roleapprove(
            Model model
    ) {
        List<MemberDto> memberlist = teacherService.getStudentList();

        model.addAttribute("page", "roleApprove");

        model.addAttribute("memberlist", memberlist);
        return "thymeleaf/teacher/roleapprove";
    }

    //학생승인에서 권한 학생으로 승인
    @PostMapping("/rolestudent")
    public String rolestudent(
            @RequestParam int member_id
    ){
        teacherService.updateStudent(member_id);

        return "redirect:/teacher/mypage/roleapprove";
    }

    //학생승인에서 권한 학생으로 거절
    @PostMapping("/roleguest")
    public String roleguest(
            @RequestParam int member_id
    ){
        teacherService.updateGuest(member_id);

        return "redirect:/teacher/mypage/roleapprove";
    }

    //정보수정 페이지로 이동
    @GetMapping("/updateProfile")
    public String updateProfile(Authentication authentication, Model model) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            model.addAttribute("member", member);
            model.addAttribute("page", "updateProfile");
        }
        return "thymeleaf/teacher/updateProfile";
    }

    //정보수정에서 프로필 정보 수정
    @PostMapping("/update")
    public String updateInfo(@ModelAttribute MemberDto memberDto, Authentication authentication, @RequestParam("upload") MultipartFile upload) {
        if (authentication != null) {
            String email = authentication.getName();
            MemberDto member = memberService.findByUsername(email);
            member.setTel(memberDto.getTel());

            if (upload != null && !upload.isEmpty()) {
                try {
                    String photo = storageService.uploadFile(bucketName, folderName, upload);
                    member.setPhoto(photo);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "redirect:/teacher/mypage/updateProfile?error=photoUploadFailed";
                }
            }

            memberService.updateMember(member);
        }
        return "redirect:/teacher/mypage/updateProfile";
    }

    //출석현황에서 캘린더 클릭시 클릭을 한 해당 날짜("date") 가져오기
    @GetMapping("/attendanceOnDate")
    @ResponseBody
    public List<AttendanceDto> getAttendanceByDate(
            @RequestParam("date") String dateStr
    ) {
        return teacherService.getAttendanceByDate(dateStr);
    }

    //출석현황에서 캘린더 클릭시 클릭을 한 해당 날짜를 가지고 출석인원 count(Map)
    @GetMapping("/attendanceCounts")
    @ResponseBody
    public Map<String, Integer> getAttendanceCounts() {
        return teacherService.getAttendanceCountsByDate();
    }
}
