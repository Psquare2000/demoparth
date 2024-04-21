package com.example.Tuesday;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendVerificationEmail(String toEmail, String userName) {
        // Generate random OTP
        String otp = RandomStringUtils.randomAlphanumeric(6).toUpperCase();

        // Create email content
        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("parthpushkar@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Verify your account");
        message.setText("Dear " + userName + ",\n\nYour verification code is: " + otp + "\n\nBest regards,\nYour Company");

        // Send email
        mailSender.send(message);

        return otp;
    }
}
