package com.OnlineExam.ExamApp.controller;

import com.OnlineExam.ExamApp.Dto.UserRegistrationDto;
import com.OnlineExam.ExamApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers()
    {
        return userService.getAllUsers();
    }
    @GetMapping("/user/{id}")
    public  ResponseEntity<?> getUsersById(@PathVariable long id){
        return  userService.getUserById(id);
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable long id, @RequestBody UserRegistrationDto userRegistrationDto){
        return userService.updateUserById(id,userRegistrationDto);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id){
        return userService.deleteUserById(id);
    }
    @GetMapping("/user/score")
    public ResponseEntity<?> getScore(@RequestParam long id){
        return userService.getScore(id);
    }
    @GetMapping("/leader-board")
    public ResponseEntity<?> getLeaderBoard(){
       return userService.getLeaderBoard();
    }

}
