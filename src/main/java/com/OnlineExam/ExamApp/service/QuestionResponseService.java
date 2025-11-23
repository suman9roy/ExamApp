package com.OnlineExam.ExamApp.service;

import com.OnlineExam.ExamApp.Dto.AnswerDto;
import com.OnlineExam.ExamApp.Dto.BulkSubmitRequest;
import com.OnlineExam.ExamApp.Entity.Question;
import com.OnlineExam.ExamApp.Entity.QuestionResponse;
import com.OnlineExam.ExamApp.Entity.Users;
import com.OnlineExam.ExamApp.Repo.QuestionRepo;
import com.OnlineExam.ExamApp.Repo.QuestionResponseRepo;
import com.OnlineExam.ExamApp.Repo.UsersRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionResponseService {
    private final QuestionResponseRepo questionResponseRepo;
    private final QuestionRepo questionRepo;
    private final UsersRepo usersRepo;

    public QuestionResponseService(QuestionResponseRepo questionResponseRepo, QuestionRepo questionRepo, UsersRepo usersRepo) {
        this.questionResponseRepo = questionResponseRepo;
        this.questionRepo = questionRepo;
        this.usersRepo = usersRepo;
    }

    public ResponseEntity<?> submitAllQuestion(BulkSubmitRequest bulkSubmitRequest) {

        List<QuestionResponse> savedResponses = new ArrayList<>();
        Users users=usersRepo.findById(bulkSubmitRequest.userId()).orElseThrow(()-> new RuntimeException("User does not exist"));
    for(AnswerDto answerDto: bulkSubmitRequest.answers()){
        QuestionResponse questionResponse=new QuestionResponse();
        Question question= questionRepo.findById(answerDto.questionId()).orElseThrow(()->new RuntimeException("Question is not exist"));
       questionResponse.setUsers(users);
        questionResponse.setQuestion(question);
        boolean isCorrect=answerDto.selectedAnswer().equalsIgnoreCase(question.getCorrectOption());
        questionResponse.setSelectedAnswer(answerDto.selectedAnswer());
        questionResponse.setCorrect(isCorrect);
       savedResponses.add(questionResponseRepo.save(questionResponse));
    }
    return new ResponseEntity<>(savedResponses, HttpStatus.OK);
    }
}
