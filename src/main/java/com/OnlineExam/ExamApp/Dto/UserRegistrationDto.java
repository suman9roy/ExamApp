package com.OnlineExam.ExamApp.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationDto {

    private String name;
    private String password;
    private  String contact;

}
