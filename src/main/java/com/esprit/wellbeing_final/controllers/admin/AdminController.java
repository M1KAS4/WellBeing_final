package com.esprit.wellbeing_final.controllers.admin;

import com.esprit.wellbeing_final.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AdminController {

    @FXML
    private Button Userbut;
    @FXML
    private Button quizListbut;
    @FXML
    private Pane rendrer;

    public void renderUserList() {
        loadView("/com/esprit/wellbeing_final/views/AdminUi/userListUi.fxml");
        setButtonActive(Userbut);
    }
    public void renderQuizList() {
        loadView("/com/esprit/wellbeing_final/views/AfficherSuj.fxml.");
        setButtonActive(Userbut);
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
        Userbut.getStyleClass().remove("active-button");
        quizListbut.getStyleClass().remove("active-button");
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
        //nomLabel.setText(user.getFirstName() + " " + user.getLastName());
        roleLabel.setText(user.getRole().name());
    }
}
