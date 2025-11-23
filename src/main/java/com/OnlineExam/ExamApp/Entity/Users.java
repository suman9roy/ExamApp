package com.OnlineExam.ExamApp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "users_id")
    private Long id;

    private String name;
    private String password;
    private  String contact;
    @Enumerated(value = EnumType.STRING)
    private Roles roles;
    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<QuestionResponse> questionResponseId;
}
