package com.esprit.wellbeing_final.controllers;

import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.services.UserService;
import com.esprit.wellbeing_final.services.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {


    @FXML
    private Parent loginMessageLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField pwPasswordField;
    @FXML
    private Label MessageLabel;

    private UserService svr = new UserServiceImp();

    public void loginButtonOnAction(ActionEvent e) {

        String email = emailTextField.getText();
        String password = pwPasswordField.getText();
        if (email.isBlank() && password.isBlank()) {
            MessageLabel.setText("Please enter both email and password !");
            MessageLabel.setVisible(true);
        } else if (email.isBlank()) {
            MessageLabel.setText("Please enter your email !");
            MessageLabel.setVisible(true);
        } else if (password.isBlank()) {
            MessageLabel.setText("Please enter your password !");
            MessageLabel.setVisible(true);
        } else {
            User u = svr.login(email, password);
            if (u != null) {
                redirectToEmployee();
            } else {
                MessageLabel.setText("Login failed. Please try again.");
                MessageLabel.setVisible(true);
            }
        }

    }

    private void redirectToEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com/esprit/wellbeing_final/views/employeeUi.fxml"));
            Parent root = loader.load();
            Scene adminScene = new Scene(root);
            Stage stage = (Stage) loginMessageLabel.getScene().getWindow();
            stage.setScene(adminScene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}