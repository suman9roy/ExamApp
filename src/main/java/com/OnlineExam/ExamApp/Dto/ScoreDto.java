package com.OnlineExam.ExamApp.Dto;

public record ScoreDto(
        int totalMarks,
        int countOfCorrectAnswer,
        int countOfWrongAnswer,
        int unattemptedQuestion,
        double percentage,
        String status
) {
}
