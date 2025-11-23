package com.OnlineExam.ExamApp.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
public class QuestionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "users_id",nullable = false)
    @JsonBackReference
    private Users users;
    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;
    private String selectedAnswer;
    private boolean isCorrect;
}
