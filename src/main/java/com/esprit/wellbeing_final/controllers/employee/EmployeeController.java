package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class EmployeeController {
    @FXML
    private Button quizbut;
    @FXML
    private Button profilebut;
    @FXML
    private Button exsbutton;
    @FXML
    private Button edcbutton;
    @FXML
    private Button exspbutton;

    @FXML
    private Pane rendrer;

    public void renderQuiz() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/quizUi.fxml");
        setButtonActive(quizbut);
    }

    public void renderProfile() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/profileUi.fxml");
        setButtonActive(profilebut);
    }

    public void renderExerice() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/excerciceUi.fxml");
        setButtonActive(exsbutton);
    }

    public void renderEducatif() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/educatifUi.fxml");
        setButtonActive(edcbutton);
    }

    public void renderExchange() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/exchangeUi.fxml");
        setButtonActive(exspbutton);
    }

    private void loadView(String resourcePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            Node menu = loader.load();
            rendrer.getChildren().setAll(menu);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    private void setButtonActive(Button button) {
        // Reset background for all buttons
        quizbut.getStyleClass().remove("active-button");
        profilebut.getStyleClass().remove("active-button");
        exsbutton.getStyleClass().remove("active-button");
        exspbutton.getStyleClass().remove("active-button");
        edcbutton.getStyleClass().remove("active-button");
        // Set background for the active button
        button.getStyleClass().add("active-button");
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
