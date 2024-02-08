package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.entities.Exercice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}
