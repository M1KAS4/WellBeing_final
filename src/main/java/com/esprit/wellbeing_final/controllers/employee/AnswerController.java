package com.esprit.wellbeing_final.controllers.employee;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class AnswerController {

    @FXML
    Label answerLabel;
    @FXML
    private RadioButton answerRadioButton;

    private ToggleGroup toggleGroup;

    public void initialize(ToggleGroup toggleGroup) {
        this.toggleGroup = toggleGroup;
        answerRadioButton.setToggleGroup(toggleGroup);
    }


    public void setAnswer(String answer){
        answerLabel.setText(answer);
        answerRadioButton.setText(answer);
    }
}
