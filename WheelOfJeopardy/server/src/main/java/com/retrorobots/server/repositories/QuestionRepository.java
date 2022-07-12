package com.retrorobots.server.repositories;

import com.retrorobots.server.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Override
    Question getReferenceById(Integer integer);
}