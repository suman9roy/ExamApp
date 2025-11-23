package com.OnlineExam.ExamApp.service;

import com.OnlineExam.ExamApp.Dto.UserGetDto;
import com.OnlineExam.ExamApp.Dto.UserLoginDto;


import com.OnlineExam.ExamApp.Dto.UserRegistrationDto;
import com.OnlineExam.ExamApp.Entity.Roles;
import com.OnlineExam.ExamApp.Entity.Users;
import com.OnlineExam.ExamApp.Repo.UsersRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UsersRepo usersRepo;

    public UserService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public ResponseEntity<?> login(UserLoginDto userLoginDto) {

        Optional<Users> optionalUsers = usersRepo.findByName(userLoginDto.getName());
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            if (bcryptPasswordEncoder().matches(userLoginDto.getPassword(),users.getPassword())) {
                return new ResponseEntity<>("Login successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bad credential", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("username not exsist, please enter correct username", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> register(UserRegistrationDto userRegistrationDto) {
        Users users=new Users(
null,
                userRegistrationDto.getName(),
                bcryptPasswordEncoder().encode(userRegistrationDto.getPassword()),
                userRegistrationDto.getContact(),
                Roles.USER,
                null

        );
        if(usersRepo.findByName(userRegistrationDto.getName()).isPresent()) {
            return new ResponseEntity<>("UseName already exsist",HttpStatus.BAD_REQUEST);
        }
        else if(usersRepo.findByContact(userRegistrationDto.getContact()).isPresent()){
            return new ResponseEntity<>("Contact is already register , Please try with different contact",HttpStatus.BAD_REQUEST);
        }
        else {
            usersRepo.save(users);
            return new ResponseEntity<>("registered successfully",HttpStatus.CREATED);
        }

    }
    public static PasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public ResponseEntity<?> getAllUsers() {
        List<Users> usersList=usersRepo.findAll();
        if(usersList.isEmpty()){
            return  new ResponseEntity<>("No User is present",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(usersList,HttpStatus.FOUND);
        }
    }

    public ResponseEntity<?> getUserById(long id) {
        Optional<Users> optionalUsers=usersRepo.findById(id);
        if(optionalUsers.isEmpty()){
            return new ResponseEntity<>("userid does not exsist",HttpStatus.NOT_FOUND);
        }
        else {
            UserGetDto userGetDto=new UserGetDto(optionalUsers.get().getId(),
                    optionalUsers.get().getName(),
                    optionalUsers.get().getContact());
            return  new ResponseEntity<>(userGetDto,HttpStatus.FOUND);
        }
    }

    public ResponseEntity<?> updateUserById(long id, UserRegistrationDto userRegistrationDto) {
        Optional<Users> optionalUsers=usersRepo.findById(id);
        if(optionalUsers.isEmpty()){
            return new ResponseEntity<>("userid does not exsist",HttpStatus.NOT_FOUND);
        }
        else {
            Users updatedUser=optionalUsers.get();
            updatedUser.setName(userRegistrationDto.getName());
            updatedUser.setContact(userRegistrationDto.getContact());
            updatedUser.setPassword(bcryptPasswordEncoder().encode(userRegistrationDto.getPassword()));
            updatedUser=usersRepo.save(updatedUser);
            return  new ResponseEntity<>(updatedUser,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> deleteUserById(long id) {
        Optional<Users> optionalUsers=usersRepo.findById(id);
        if(optionalUsers.isEmpty()){
            return new ResponseEntity<>("userid does not exsist",HttpStatus.NOT_FOUND);
        }
        else {
            usersRepo.deleteById(id);
            return  new ResponseEntity<>("User deleted successfully",HttpStatus.OK);
        }
    }
}
