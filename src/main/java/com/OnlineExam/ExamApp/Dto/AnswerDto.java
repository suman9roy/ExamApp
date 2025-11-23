package com.OnlineExam.ExamApp.Dto;


public record AnswerDto(
        Long questionId,
        String selectedAnswer   // A/B/C/D/null
) {}

