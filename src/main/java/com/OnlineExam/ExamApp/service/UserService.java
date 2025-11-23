package com.OnlineExam.ExamApp.service;

import com.OnlineExam.ExamApp.Dto.*;


import com.OnlineExam.ExamApp.Entity.QuestionResponse;
import com.OnlineExam.ExamApp.Entity.Roles;
import com.OnlineExam.ExamApp.Entity.Users;
import com.OnlineExam.ExamApp.Repo.QuestionRepo;
import com.OnlineExam.ExamApp.Repo.UsersRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UsersRepo usersRepo;
    private final QuestionRepo questionRepo;
    public UserService(UsersRepo usersRepo, QuestionRepo questionRepo) {
        this.usersRepo = usersRepo;
        this.questionRepo = questionRepo;
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
                false,0,
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

    public ResponseEntity<?> getScore(long id) {
        int totalMarks=questionRepo.findAll().size();
        List<QuestionResponse> questionResponseList=usersRepo.findById(id).orElseThrow(()->new RuntimeException("user id does not exist")).getQuestionResponseId();
        int countOfCorrectAnswer= (int) questionResponseList.stream()
                .filter(QuestionResponse::isCorrect).count();
        int countOfWrongAnswer=(int) questionResponseList.stream().filter(x->!x.isCorrect()).count();
        int unattemptedQuestion=totalMarks-(countOfCorrectAnswer+countOfWrongAnswer);
        double percentage=(countOfCorrectAnswer-0.25*countOfWrongAnswer)*100/totalMarks;
        String status=percentage>40?"pass":"fail";
        ScoreDto scoreDto=new ScoreDto(
               totalMarks,
               countOfCorrectAnswer,
                countOfWrongAnswer,
                unattemptedQuestion,
                percentage,
                status


        );
        return new ResponseEntity<>(scoreDto,HttpStatus.OK);
    }

    public ResponseEntity<?> getLeaderBoard() {
        List<LeaderBoardDto> leaderboard = usersRepo.findAll()
                .stream()
                .filter(Users::isAttempted)
                .sorted(Comparator.comparing(Users::getScore).reversed())
                .map(u -> new LeaderBoardDto(u.getId(), u.getName(), u.getScore()))
                .toList();

        return ResponseEntity.ok(leaderboard);
    }
}
