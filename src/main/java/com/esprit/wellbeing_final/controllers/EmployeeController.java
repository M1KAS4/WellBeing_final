package com.esprit.wellbeing_final.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class EmployeeController {
    @FXML

    Pane rendrer ;
    public void renderQuiz() throws IOException {
        rendrer.getChildren().removeAll();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginController.class.getResource("/com/esprit/wellbeing_final/views/quiz.fxml")));
        Node menu = loader.load();
        rendrer.getChildren().setAll(menu);

    }
}
