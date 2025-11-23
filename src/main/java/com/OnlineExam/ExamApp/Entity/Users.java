package com.OnlineExam.ExamApp.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private boolean attempted;
    private double score;
    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<QuestionResponse> questionResponseId;

}
