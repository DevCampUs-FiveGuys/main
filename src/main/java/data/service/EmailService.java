package data.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    
    //메일 제목
    private final String SUBJECT ="[DevCampUs] 인증메일";
    
    //메일 보내기
    public boolean sendCertificationMAil (String email, String certificationNumber){

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            String htmlContent = getCertificationMessage(certificationNumber);

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);

        }catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;

    }

    private String getCertificationMessage (String certificationNumber){

        //메일 내용
        String certificationMessage ="";

        //메일 내용 추가
        certificationMessage += "<h1 style='text-align : center;'>[DevCampUs] 인증메일</h1>";
        certificationMessage += "<h3 style='text-algin : center;'>인증 코드 : <strong style=;='font-size : 32px; letter-spacing : 8px;'>" + certificationNumber + "</strong></h3>";

        return certificationMessage;
    }

}
