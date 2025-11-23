package com.OnlineExam.ExamApp.Dto;

import java.util.List;

public record BulkSubmitRequest(
        Long userId,
        List<AnswerDto> answers
) {}

