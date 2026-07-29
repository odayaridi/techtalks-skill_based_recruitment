package org.example.techtalksskillbasedrecruitment.modules.user.Email;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final String senderEmail;

    public EmailService(
            JavaMailSender javaMailSender,
            @Value("${spring.mail.username}") String senderEmail
    ) {
        this.javaMailSender = javaMailSender;
        this.senderEmail = senderEmail;
    }

    public void sendPasswordResetEmail(
            String recipientEmail,
            String resetLink
    ) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(recipientEmail);
        message.setSubject("SkillMatch Password Reset");

        message.setText(
                """
                Hello,

                We received a request to reset your SkillMatch password.

                Use the link below to create a new password:

                %s

                This link will expire in 15 minutes.

                If you did not request a password reset, ignore this email.

                SkillMatch Team
                """.formatted(resetLink)
        );

        javaMailSender.send(message);
    }
}