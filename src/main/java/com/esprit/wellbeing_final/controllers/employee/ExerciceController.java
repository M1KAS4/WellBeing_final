package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.controllers.auth.LoginController;
import com.esprit.wellbeing_final.entities.Exercice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.Objects;

public class ExerciceController {
    @FXML
    Pane exercices;

    @FXML
    WebView viewer;

    Pane pane;

    Exercice exercice;


    public void setData(Pane pane, Exercice ex) {
        this.exercice = ex;
        this.pane = pane;
    }

    @FXML
    public void init() throws IOException {

        pane.getChildren().removeAll();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/wellbeing_final/views/EmployeeUi/exerciceContent.fxml"));
        Node menu = loader.load();
        pane.getChildren().setAll(menu);
        ExerciceContentController controller = loader.getController();
        controller.setData(exercice);

    }
}
