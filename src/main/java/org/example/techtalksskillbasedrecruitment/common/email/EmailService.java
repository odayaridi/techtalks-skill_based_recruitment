package org.example.techtalksskillbasedrecruitment.common.email;

import org.example.techtalksskillbasedrecruitment.common.exceptions.EmailSendException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String recipientEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(recipientEmail);
        message.setSubject("SkillMatch Password Reset");
        message.setText(
                "Hello,\n\n" +
                        "We received a request to reset your SkillMatch password.\n" +
                        "Use the link below to create a new password:\n" +
                        resetLink + "\n\n" +
                        "This link will expire in 15 minutes.\n\n" +
                        "If you did not request a password reset, ignore this email.\n\n" +
                        "SkillMatch Team"
        );

        try {
            mailSender.send(message);
        } catch (MailException ex) {
            throw new EmailSendException("Unable to send password reset email. Please try again later.", ex);
        }
    }
}
