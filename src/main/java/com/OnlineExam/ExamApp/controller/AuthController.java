package com.OnlineExam.ExamApp.controller;

import com.OnlineExam.ExamApp.Dto.UserLoginDto;

import com.OnlineExam.ExamApp.Dto.UserRegistrationDto;
import com.OnlineExam.ExamApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto){
        return userService.login(userLoginDto);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto userRegistrationDto){
        return userService.register(userRegistrationDto);
    }
}
