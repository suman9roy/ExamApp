package com.OnlineExam.ExamApp.controller;

import com.OnlineExam.ExamApp.Dto.QuestionPostDto;
import com.OnlineExam.ExamApp.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/all-questions")
    public ResponseEntity<?> getAllQuestion(){
        return questionService.getAllQuestion();
    }
    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionPostDto questionPostDto){
        return questionService.addQuestion(questionPostDto);
    }
    @GetMapping("/question/{id}")
    public  ResponseEntity<?> getQuestionById(@PathVariable long id){
        return questionService.getQuestionById(id);
    }
    @PutMapping("/question/{id}")
    public  ResponseEntity<?> updateQuestionById(@PathVariable long id,@RequestBody QuestionPostDto questionPostDto){
        return questionService.updateQuestionById(id,questionPostDto);
    }
    @DeleteMapping("/question/{id}")
    public  ResponseEntity<?> deleteQuestionById(@PathVariable long id){
        return questionService.deleteBookById(id);
    }
}
