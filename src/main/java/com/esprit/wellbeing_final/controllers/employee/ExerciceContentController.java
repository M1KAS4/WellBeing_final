package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.entities.Exercice;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class ExerciceContentController {

    @FXML
    Label title;

    @FXML
    Label duration;
    @FXML
    Label desc;
    @FXML
    Label type;
    @FXML
    Label link;
    @FXML
    private WebView webView;

    @FXML
    private AnchorPane myanchor;

    @FXML
    private Button goback;

    public void setData(Exercice exercice) {
        title.setText(exercice.getTitle());
        desc.setText(exercice.getDescription());
        duration.setText(exercice.getDuration());
        type.setText(exercice.getType());
        link.setText(exercice.getLink());
    }

    public void startVideo() {
        String fullVideoUrl = link.getText();
        String videoId = extractYouTubeVideoId(fullVideoUrl);
        WebEngine webEngine = webView.getEngine();
        String embedUrl = "https://www.youtube.com/embed/" + videoId + "?rel=0&autoplay=1";
        webEngine.load(embedUrl);
        webView.setVisible(true);
    }

    private String extractYouTubeVideoId(String videoUrl) {
        // Use a regex to extract the YouTube video ID
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed\\?video_id=|\\?v=|\\&v=|youtu.be\\/|watch\\?v=|\\/v\\/|e\\/|watch\\?v=|\\/videos\\/|embed\\/|youtu.be\\/|v=|\\/e\\/|watch\\?v=|&v=|%2Fvideos%2F|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed\\?video_id=|\\?v=|\\&v=|%2Fvideos%2F)([^#\\&\\?\\n]*[^#\\&\\?\\n\\W])";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(videoUrl);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    public void resizePane(double newWidth, double newHeight) {
        myanchor.setPrefWidth(newWidth);
        myanchor.setPrefHeight(newHeight);
    }



    @FXML
    public void GoBack() throws IOException {
        // Load the ExercicesList.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/wellbeing_final/views/EmployeeUi/ExercicesList.fxml"));
        Parent root = loader.load();

        // Get the stage from the button
        Stage stage = (Stage) goback.getScene().getWindow();

        // Get the existing scene
        Scene scene = goback.getScene();

        // Set the root of the existing scene to the loaded ExercicesList.fxml
        scene.setRoot(root);

        // Optional: You may want to update the data in ExerciceListController here
        // ExerciceListController listController = loader.getController();
        // listController.updateData();
    }

}
