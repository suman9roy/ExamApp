package com.OnlineExam.ExamApp.Repo;

import com.OnlineExam.ExamApp.Entity.QuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionResponseRepo extends JpaRepository<QuestionResponse,Long> {
}
