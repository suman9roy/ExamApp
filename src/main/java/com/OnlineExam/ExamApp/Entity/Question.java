package com.OnlineExam.ExamApp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private Long id;
    private String questionName;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;
    @OneToMany(mappedBy = "question",orphanRemoval = true)
    private List<QuestionResponse> questionResponseList;

}
