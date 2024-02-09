package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.entities.Ressources;
import com.esprit.wellbeing_final.services.RessourceServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RessourceController {
    private final RessourceServiceImpl ressourceService;
    private Ressources ressources;

    @FXML
    Label titleLabel;
    @FXML
    Label viewsLabel;
    @FXML
    TextArea descriptionArea;
    @FXML
    Hyperlink linkHyperlink;

    private WebView webView;
    private WebEngine webEngine;

    public RessourceController() {
        this.ressourceService = new RessourceServiceImpl();
    }

    public void setInfos(Ressources res) {
        this.ressources = res;
        titleLabel.setText(res.getTitre());
        viewsLabel.setText(res.getViews().toString());
        descriptionArea.setText(res.getDescription());
        linkHyperlink.setText(res.getLien());
        linkHyperlink.setOnAction(e -> {
            openLinkInBrowser(res.getLien());
        });
    }

    private void openLinkInBrowser(String link) {

        webView = new WebView();
        webEngine = webView.getEngine();
        BorderPane root = new BorderPane();
        root.setCenter(webView);

        Scene scene = new Scene(root, 800, 600);
        String fullVideoUrl = link;
        String videoId = extractYouTubeVideoId(fullVideoUrl);
        WebEngine webEngine = webView.getEngine();
        String embedUrl = "https://www.youtube.com/embed/" + videoId + "?rel=0&autoplay=1";
        webEngine.load(embedUrl);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            webEngine.load("https://www.google.com");
            primaryStage.setScene(new Scene(new BorderPane(), 1, 1));
            System.out.println("Video closed");
            ressourceService.incrementRessourceViews(ressources);
        });

        primaryStage.show();

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