package com.esprit.wellbeing_final.services;


import com.esprit.wellbeing_final.entities.Question;
import com.esprit.wellbeing_final.entities.QuestionsAnswer;
import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionAnswersImpl implements QuestionAnswerService {
    @Override
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        Map<String, Integer> categories = new HashMap<>();
        categories.put("Workload", 4);
        categories.put("Social Support", 3);
        categories.put("Work-Life Balance", 2);
        categories.put("Autonomy at Work", 4);
        categories.put("Recognition and Rewards", 3);
        categories.put("Communication and Feedback", 4);
        categories.put("Career Growth Opportunities", 3);
        categories.put("Job Security", 2);
        categories.put("Workplace Environment", 4);
        categories.put("Coping Mechanisms", 3);

        for (Map.Entry<String, Integer> entry : categories.entrySet()) {
            String category = entry.getKey();

            String query = "SELECT * FROM question WHERE category = ? ORDER BY RAND() LIMIT 1";

            try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
                preparedStatement.setString(1, category);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Question question = new Question();
                    question.setId_question(resultSet.getLong("id_question"));
                    question.setQuestiontext(resultSet.getString("questiontext"));
                    question.setCategory(resultSet.getString("category"));
                    questions.add(question);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println(questions);
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

    @Override
    public void saveScore(User u, Long c) throws SQLException {
        try {
            // Check if the row with id_user exists
            String selectSql = "SELECT * FROM stress WHERE id_user = ?";
            PreparedStatement selectStatement = MyConnection.getInstance().getCnx().prepareStatement(selectSql);
            selectStatement.setLong(1, u.getId());

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Row exists, perform update
                String updateSql = "UPDATE stress SET score = ?, date = NOW() WHERE id_user = ?";
                PreparedStatement updateStatement = MyConnection.getInstance().getCnx().prepareStatement(updateSql);

                updateStatement.setLong(1, c);
                updateStatement.setLong(2, u.getId());

                updateStatement.executeUpdate();
            } else {
                // Row doesn't exist, perform insert
                String insertSql = "INSERT INTO stress (id_user, score, date) VALUES (?, ?, NOW())";
                PreparedStatement insertStatement = MyConnection.getInstance().getCnx().prepareStatement(insertSql);

                insertStatement.setLong(1, u.getId());
                insertStatement.setLong(2, c);

                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }
    }

    @Override
    public Long getScoreByUserId(User u) throws SQLException {
        Long score = null;

        try {
            // Check if the row with id_user exists
            String selectSql = "SELECT score FROM stress WHERE id_user = ?";
            PreparedStatement selectStatement = MyConnection.getInstance().getCnx().prepareStatement(selectSql);
            selectStatement.setLong(1, u.getId());

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Row exists, retrieve the score
                score = resultSet.getLong("score");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }

        return score;
    }
}
