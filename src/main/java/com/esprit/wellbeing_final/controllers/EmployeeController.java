package com.esprit.wellbeing_final.controllers;

import com.esprit.wellbeing_final.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class EmployeeController {
    @FXML

    Pane rendrer;

    public void renderQuiz() throws IOException {
        rendrer.getChildren().removeAll();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginController.class.getResource("/com/esprit/wellbeing_final/views/quizUi.fxml")));
        Node menu = loader.load();
        rendrer.getChildren().setAll(menu);

    }

    @FXML
    Button quizbut;

    public void ChangeColor() {
        quizbut.setOnMouseClicked(e -> {
            quizbut.setStyle("-fx-background-color: #0a433a; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: white;");
        });

    }

    private User currentUser;
    @FXML
    private Label nomLabel;

    @FXML
    private Label roleLabel;

    public void setUserData(User user) {
        currentUser = user;
        nomLabel.setText(user.getFirstName() + " " + user.getLastName());
        roleLabel.setText(user.getRole().name());
    }
}
