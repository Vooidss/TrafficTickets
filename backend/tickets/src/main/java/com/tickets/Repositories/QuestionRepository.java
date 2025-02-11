package com.tickets.Repositories;

import com.tickets.Models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {
    Questions findByHashImage(String hashImage);
}
