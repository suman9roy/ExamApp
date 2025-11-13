package com.OnlineExam.ExamApp.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class QuestionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "users_id",nullable = false)
    private Users users;
    private long questionId;
    private String selectedAnswer;
}
