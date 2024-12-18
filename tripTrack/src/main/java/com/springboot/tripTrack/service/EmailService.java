package com.springboot.tripTrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendPasswordResetEmail(String to, String newPassword) {
        String subject = "비밀번호 초기화 안내";
        String content = "<p>비밀번호가 초기화 되었습니다.</p>"
                        + "<p>임시 비밀번호는 <strong>" + newPassword + "</strong> 입니다.</p>"
                        + "<p>임시 비밀번호로 로그인 후, 마이페이지 회원정보에서 "
                        + "비밀번호 중복확인 후 수정하기 버튼을 눌러서 비밀번호를 변경해주세요.</p>";

        try {
            MimeMessage message = emailSender.createMimeMessage();
            // 문자 인코딩을 HTML내용으로 인코딩 해주는 역할
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setHeader("Content-Transfer-Encoding", "quoted-printable");
            
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);  // true는 내용이 HTML형식이라는것을 알려주는것이다.
            emailSender.send(message);
        } catch (MessagingException e) {
            // 예외 처리 로직
            e.printStackTrace();
        }
    }
}
