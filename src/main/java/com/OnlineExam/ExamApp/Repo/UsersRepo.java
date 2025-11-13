package com.OnlineExam.ExamApp.Repo;

import com.OnlineExam.ExamApp.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {
    Optional<Users> findByName(String name);
}
