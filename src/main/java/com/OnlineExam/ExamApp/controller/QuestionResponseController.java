package com.OnlineExam.ExamApp.controller;

import com.OnlineExam.ExamApp.Dto.BulkSubmitRequest;
import com.OnlineExam.ExamApp.Entity.QuestionResponse;
import com.OnlineExam.ExamApp.service.QuestionResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam")
public class QuestionResponseController {

    private final QuestionResponseService questionResponseService;

    public QuestionResponseController(QuestionResponseService questionResponseService) {
        this.questionResponseService = questionResponseService;
    }
    @PostMapping("/submit")
    public ResponseEntity<?> submitAllQuestion(@RequestBody BulkSubmitRequest bulkSubmitRequest){
        return questionResponseService.submitAllQuestion(bulkSubmitRequest);
    }

}
