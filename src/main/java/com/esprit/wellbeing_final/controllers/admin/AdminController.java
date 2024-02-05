package com.esprit.wellbeing_final.controllers.admin;

import com.esprit.wellbeing_final.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminController {
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
