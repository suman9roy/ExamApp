package com.OnlineExam.ExamApp.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserGetDto {
    private Long id;
    private String name;
    private  String contact;

}
