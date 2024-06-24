package controller.member;

import data.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice // 전역함수로 사용 가능
@RequiredArgsConstructor
public class GlobalController {
    private final MemberService memberService;

    @ModelAttribute
    public void member (Authentication authentication, Model model) {
        if(authentication != null) {
            String email = authentication.getName(); // 인증된 사용자의 이메일

            String userName = memberService.findByUsername(email).getName(); // 인증된 사용자의 이메일이 가지고 있는 name
            String photo = memberService.findByUsername(email).getPhoto(); // 인증된 사용자의 이메일이 가지고 있는 photo
            if(photo == null) {
                photo = "/images/empty.png";
            }
            //System.out.println(memberService.findByUsername(email));
            model.addAttribute("userName", userName);
            model.addAttribute("photo", photo);
        }
    }
}
