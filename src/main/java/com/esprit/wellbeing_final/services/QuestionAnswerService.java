package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.Question;
import com.esprit.wellbeing_final.entities.QuestionsAnswer;
import com.esprit.wellbeing_final.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface QuestionAnswerService {

    List<Question> getQuestions();
    List<QuestionsAnswer> getAnswersById(Long id);

    QuestionsAnswer getAnswerByText(String answerText,Long id);

    void saveScore(User u, Long c) throws SQLException;

    Long getScoreByUserId(User u) throws SQLException;
}
