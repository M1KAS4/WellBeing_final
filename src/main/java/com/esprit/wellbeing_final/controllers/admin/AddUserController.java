package com.esprit.wellbeing_final.controllers.admin;


import com.esprit.wellbeing_final.entities.Role;
import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.services.UserService;
import com.esprit.wellbeing_final.services.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

// AddUserController.java
    public class AddUserController {
        @FXML
        private TextField firstNameTextField;
        @FXML private TextField lastNameTextField;
        @FXML private TextField emailTextField;
        @FXML private PasswordField passwordField;
        @FXML private ChoiceBox<Role> roleChoiceBox;

        private UserService userService = new UserServiceImp();

        @FXML
        private void initialize() {
            roleChoiceBox.getItems().addAll(Role.EMPLOYEE, Role.COACH);
        }

    @FXML
    private void handleAddUser(ActionEvent event) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();
        Role role = roleChoiceBox.getValue();

        User newUser = new User(null, firstName, lastName, email, password, role);
        userService.createUser(newUser);

        // Display confirmation message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Created");
        alert.setHeaderText(null);
        alert.setContentText("User created successfully!");
        alert.showAndWait();

        // You can also navigate back to the admin interface here if needed
    }
    @FXML
    private void handleBackButtonClick(ActionEvent event) {
        try {
            Parent adminParent = FXMLLoader.load(getClass().getResource("/com/esprit/wellbeing_final/views/AdminUi/userListUi.fxml"));
            Scene adminScene = new Scene(adminParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(adminScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}


