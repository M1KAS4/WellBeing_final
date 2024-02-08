package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class ProfileController {

    @FXML
    private TextField BirthdayField;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField LastField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField RoleField;

    private User currentUser;

    @FXML
    public void initialize() {
        setUserData(currentUser);
    }

    public void setUserData(User currentUser) {
        // Set user data in the controller
        if (currentUser != null) {
            NameField.setText(currentUser.getFirstName());
            LastField.setText(currentUser.getLastName());
            EmailField.setText(currentUser.getEmail());
            RoleField.setText(currentUser.getRole().name());

        }
    }
}
