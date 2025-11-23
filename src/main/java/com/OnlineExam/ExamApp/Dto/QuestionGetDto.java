package com.OnlineExam.ExamApp.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionGetDto {
    private Long id;
    private String questionName;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
}
