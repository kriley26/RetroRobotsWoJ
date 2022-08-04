package com.retrorobots.server.repositories;

import com.retrorobots.server.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Override
    Question getReferenceById(Integer integer);

    @Query("SELECT DISTINCT q.category FROM Question q WHERE q.round = \'Jeopardy!\'")
    List<String> findDistinctCategory();

    @Query("SELECT q from Question q where q.round = \'Jeopardy!\' AND q.category = ?1")
    List<Question> findCategoryQuestions(String category);
}