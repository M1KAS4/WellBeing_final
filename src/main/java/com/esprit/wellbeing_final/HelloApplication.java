package com.esprit.wellbeing_final;

import com.esprit.wellbeing_final.tools.MyConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        InputStream iconStream = getClass().getResourceAsStream("/com/esprit/wellbeing_final/images/oasis.png");
        Image icon = new Image(iconStream);

        stage.getIcons().add(icon);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/esprit/wellbeing_final/views/loginUi.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 780, 460);
        stage.setTitle("Oasis");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        System.out.println(MyConnection.getInstance().getCnx());
    }
}