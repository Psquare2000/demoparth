package com.example.Tuesday;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private UserDAO userDAO;
//    @Autowired
//    private VaultOtpService vaultOtpService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        System.out.println(user.getName() + "  *RIZZAB*");
        return userService.saveUser(user);
    }

    @GetMapping("/getAll")
    public List<Map<String, Object>> getAllUsers() {
        return userDAO.findAllUsers();
    }


    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam  String name) {
        String otp = emailService.sendVerificationEmail(email, name);
        otpService.storeOtp(email,otp);
//        vaultOtpService.storeOtp(email,otp);
        System.out.println(otp+ "    *RIZZAB*"+name);
        return "OTP sent to " + email + ". Please verify to complete registration.";
    }


    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isOtpValid = otpService.verifyOtp(email, otp);
//        boolean isOtpValid = vaultOtpService.verifyOtp(email, otp);
        if (isOtpValid) {
            return "OTP verified successfully!";
        } else {
            return "Invalid OTP. Please try again.";
        }
    }
}
