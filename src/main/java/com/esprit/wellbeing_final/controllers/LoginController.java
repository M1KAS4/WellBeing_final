package com.esprit.wellbeing_final.controllers;

import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.services.UserService;
import com.esprit.wellbeing_final.services.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private UserService svr = new UserServiceImp();

    public void loginButtonOnAction(ActionEvent e) {

        String email = emailTextField.getText();
        String password = pwPasswordField.getText();
        User u = svr.login(email, password);
        if (u != null) {
            redirectToEmployee();
        } else {
            System.out.println("Failed");
        }


        //if (emailTextField.getText().isBlank() == false && pwPasswordField.getText().isBlank() == false){
        //loginMessageLabel.setText("Loging in!");
        // } else if (emailTextField.getText().isBlank() == true && pwPasswordField.getText().isBlank() == false) {
        //   loginMessageLabel.setText("Please enter your email!");
        //} else if (emailTextField.getText().isBlank() == false && pwPasswordField.getText().isBlank() == true) {
        //  loginMessageLabel.setText("Please enter your password!");
        //} else {
        // loginMessageLabel.setText("Please enter both email and password!");
        //}
    }

    private void redirectToEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com/esprit/wellbeing_final/views/employee.fxml"));
            Parent root = loader.load();
            Scene adminScene = new Scene(root);
            Stage stage = (Stage) loginMessageLabel.getScene().getWindow();
            stage.setScene(adminScene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}