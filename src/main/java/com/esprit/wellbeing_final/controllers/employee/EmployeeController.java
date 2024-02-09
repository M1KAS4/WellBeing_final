package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
    private Button appbutton;

    @FXML
    private Pane rendrer;

    @FXML
    private HBox HProfile;
    @FXML
    private HBox HQuiz;
    @FXML
    private HBox HExs;
    @FXML
    private HBox HEdc;
    @FXML
    private HBox HExch;
    @FXML
    private HBox HApp;


    public void renderQuiz() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/quizUi.fxml");
        setButtonActive(quizbut, HQuiz);
    }

    public void renderProfile() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/profileUi.fxml");
        setButtonActive(profilebut, HProfile);
    }

    public void renderExercice() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/ExercicesList.fxml");
        setButtonActive(exsbutton, HExs);
    }

    public void renderEducatif() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/resources_rendrer.fxml");
        setButtonActive(edcbutton, HEdc);
    }

    public void renderExchange() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/exchangeUi.fxml");
        setButtonActive(exspbutton, HExch);
    }

    public void renderAppointment() {
        loadView("/com/esprit/wellbeing_final/views/EmployeeUi/ListeRendevouz.fxml");
        setButtonActive(appbutton, HApp);
    }

    private void loadView(String resourcePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            Node menu = loader.load();
            rendrer.getChildren().setAll(menu);
            Object controller = loader.getController();
            if (controller instanceof QuizController) {
                ((QuizController) controller).setUserData(this.currentUser);
            } else if (controller instanceof ProfileController) {
                ((ProfileController) controller).setUserData(this.currentUser);
            } else if (controller instanceof SuggestedExercices) {
                ((SuggestedExercices) controller).initializeWithData(this.currentUser);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setButtonActive(Button button, HBox hbox) {
        // Reset background for all buttons
        quizbut.getStyleClass().remove("active-button");
        HQuiz.getStyleClass().remove("active-button");

        profilebut.getStyleClass().remove("active-button");
        HProfile.getStyleClass().remove("active-button");

        exsbutton.getStyleClass().remove("active-button");
        HExs.getStyleClass().remove("active-button");

        exspbutton.getStyleClass().remove("active-button");
        HExch.getStyleClass().remove("active-button");

        edcbutton.getStyleClass().remove("active-button");
        HEdc.getStyleClass().remove("active-button");
        appbutton.getStyleClass().remove("active-button");
        HApp.getStyleClass().remove("active-button");
        // Set background for the active button
        button.getStyleClass().add("active-button");
        hbox.getStyleClass().add("active-button");
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


    public void signout() {
        try {
            // Load the login page FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/esprit/wellbeing_final/views/loginUi.fxml"));
            Parent loginPage = fxmlLoader.load();

            // Get the current stage
            Stage stage = (Stage) rendrer.getScene().getWindow();

            // Set the login scene in the stage
            Scene loginScene = new Scene(loginPage);
            stage.setScene(loginScene);

            // Reset user data
            currentUser = null;
            nomLabel.setText("");
            roleLabel.setText("");

            // Clear active states
            clearActiveStates();

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void clearActiveStates() {
        // Reset background for all buttons
        quizbut.getStyleClass().remove("active-button");
        HQuiz.getStyleClass().remove("active-button");

        profilebut.getStyleClass().remove("active-button");
        HProfile.getStyleClass().remove("active-button");

        exsbutton.getStyleClass().remove("active-button");
        HExs.getStyleClass().remove("active-button");

        exspbutton.getStyleClass().remove("active-button");
        HExch.getStyleClass().remove("active-button");

        edcbutton.getStyleClass().remove("active-button");
        HEdc.getStyleClass().remove("active-button");
        appbutton.getStyleClass().remove("active-button");
        HApp.getStyleClass().remove("active-button");
    }


}
