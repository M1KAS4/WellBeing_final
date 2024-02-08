package com.esprit.wellbeing_final.controllers.auth;

import com.esprit.wellbeing_final.controllers.admin.AdminController;
import com.esprit.wellbeing_final.controllers.coach.CoachController;
import com.esprit.wellbeing_final.controllers.employee.EmployeeController;
import com.esprit.wellbeing_final.controllers.employee.QuizController;
import com.esprit.wellbeing_final.entities.Role;
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
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {


    @FXML
    private Parent loginMessageLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField pwPasswordField;
    @FXML
    private Label MessageLabel;
    @FXML
    private ToggleButton togglePasswordButton;
    @FXML
    private TextField pwTextField;
    private UserService svr = new UserServiceImp();

    public void initialize() {
        togglePasswordButton.setOnAction(event -> {
            if (togglePasswordButton.isSelected()) {
                // Show the password
                pwPasswordField.setManaged(false);
                pwPasswordField.setVisible(false);

                pwTextField.setText(pwPasswordField.getText());
                pwTextField.setVisible(true);
                pwTextField.setManaged(true);

            } else {
                pwTextField.setVisible(false);
                pwTextField.setManaged(false);
                pwPasswordField.setManaged(true);
                pwPasswordField.setText(pwTextField.getText());
                pwPasswordField.setVisible(true);

            }
        });

    }

    public void loginButtonOnAction(ActionEvent e) {
        String email = emailTextField.getText();
        String password = pwPasswordField.getText();

        // Check if email is not a valid email format
        if (!isValidEmail(email)) {
            MessageLabel.setText("Please enter a valid email address !");
            MessageLabel.setVisible(true);
            return; // Exit the method if email is not valid
        }

        // Check if both email and password are blank
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
                redirectToInterface(u.getRole(), u);
            } else {
                MessageLabel.setText("Email and password do not match. Please try again.");
                MessageLabel.setVisible(true);
            }
        }
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    private void redirectToInterface(Role role, User user) {
        try {
            String resourcePath = "/com/esprit/wellbeing_final/views/";
            String fxmlFile = ""; // Initialize fxmlFile variable

            // Set fxmlFile based on role
            switch (role) {
                case EMPLOYEE:
                    fxmlFile = "EmployeeUi/employeeUi.fxml";
                    break;
                case COACH:
                    fxmlFile = "CoachUi/coachUi.fxml";
                    break;
                case ADMIN:
                    fxmlFile = "AdminUi/adminUi.fxml";
                    break;
                default:
                    break;
            }

            // Check if fxmlFile is not empty before proceeding
            if (!fxmlFile.isEmpty()) {
                FXMLLoader loader = new FXMLLoader(LoginController.class.getResource(resourcePath + fxmlFile));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) loginMessageLabel.getScene().getWindow();
                stage.setScene(scene);

                // Set user data to the controller if necessary
                Object controller = loader.getController();
                if (controller instanceof EmployeeController) {
                    ((EmployeeController) controller).setUserData(user);
                    ((EmployeeController) controller).renderProfile();
                    System.out.println("tet");
                } else if (controller instanceof AdminController) {
                    ((AdminController) controller).setUserData(user);
                    ((AdminController) controller).renderUserList();
                } else if (controller instanceof CoachController) {
                    ((CoachController) controller).setUserData(user);
                } else if (controller instanceof QuizController) {

                    ((QuizController) controller).setUserData(user);
                }

                // Show the stage
                stage.show();
            } else {
                System.out.println("Invalid role specified.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}