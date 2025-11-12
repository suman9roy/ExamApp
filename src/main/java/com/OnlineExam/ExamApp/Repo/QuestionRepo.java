package com.OnlineExam.ExamApp.Repo;

import com.OnlineExam.ExamApp.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Long> {
}
