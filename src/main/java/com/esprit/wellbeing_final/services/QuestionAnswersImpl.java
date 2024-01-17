package com.esprit.wellbeing_final.services;


import com.esprit.wellbeing_final.entities.Question;
import com.esprit.wellbeing_final.entities.QuestionsAnswer;
import com.esprit.wellbeing_final.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionAnswersImpl implements QuestionAnswerService {
    @Override
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        String query = "SELECT * FROM question";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Question question = new Question();
                question.setId_question(resultSet.getLong("id_question"));
                question.setQuestiontext(resultSet.getString("questiontext"));
                question.setCategory(resultSet.getString("category"));

                questions.add(question);
            }

        } catch ( SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    @Override
    public List<QuestionsAnswer> getAnswersById(Long id) {
        List<QuestionsAnswer> answers = new ArrayList<>();

        String query = "SELECT * FROM questionsanswers WHERE answerId = ?";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    QuestionsAnswer answer = new QuestionsAnswer();
                    answer.setId(resultSet.getLong("id"));
                    answer.setAnswerId(resultSet.getLong("answerId"));
                    answer.setAnswerText(resultSet.getString("answerText"));
                    answer.setCoef(resultSet.getInt("coef"));

                    answers.add(answer);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answers;
    }

    @Override
    public QuestionsAnswer getAnswerByText(String answerText, Long id) {
        QuestionsAnswer answer = null;
        System.out.println(answerText);
        System.out.println(id);

        String query = "SELECT * FROM questionsanswers WHERE answerText = ? AND answerId = ?";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            preparedStatement.setString(1, answerText);
            preparedStatement.setLong(2, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println(resultSet.getRow());
                if (resultSet.next()) {
                    answer = new QuestionsAnswer();
                    answer.setId(resultSet.getLong("id"));
                    answer.setAnswerId(resultSet.getLong("answerId"));
                    answer.setAnswerText(resultSet.getString("answerText"));
                    answer.setCoef(resultSet.getInt("coef"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answer;
    }
}
