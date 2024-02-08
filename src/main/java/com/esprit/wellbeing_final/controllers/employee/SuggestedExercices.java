package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.entities.Exercice;
import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.services.ExercicesImpl;
import com.esprit.wellbeing_final.services.ExercicesService;
import com.esprit.wellbeing_final.services.QuestionAnswerService;
import com.esprit.wellbeing_final.services.QuestionAnswersImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SuggestedExercices {


    @FXML
    Pane myPane;


    @FXML
    GridPane grid;

    ExercicesService exercicesService = new ExercicesImpl();
    QuestionAnswerService questionAnswerService = new QuestionAnswersImpl();

    public void initializeWithData(User user) throws SQLException {
        Long score = questionAnswerService.getScoreByUserId(user);
        String category = "";
        if (score < 30) {
            category = "low";
        }
        if (score > 29 && score < 60) {
            category = "medium";
        }
        if (score > 59 && score < 90) {
            category = "high";
        }
        if (score > 89) {
            category = "very high";
        }
        List<Exercice> exercices = exercicesService.getExercicesByCategory(category);
        Node[] nodes = new Node[exercices.size()];
        int column = 0;
        int row = 1;
        for (int i = 0; i < nodes.length; i++) {
            try {

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/esprit/wellbeing_final/views/EmployeeUi/exercice.fxml"));
                nodes[i] = loader2.load();

                if (column == 3) {
                    column = 0;
                    row++;
                }
                ExerciceController exerciceController = loader2.getController();
                exerciceController.setData(myPane, exercices.get(i));

                grid.add(nodes[i], column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);


            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
