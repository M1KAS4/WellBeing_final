package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.controllers.auth.LoginController;
import com.esprit.wellbeing_final.entities.Ressources;
import com.esprit.wellbeing_final.services.RessourceService;
import com.esprit.wellbeing_final.services.RessourceServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RessourcesController implements Initializable {

    @FXML
    VBox resRendrer;

    RessourceService ressourceService = new RessourceServiceImpl();




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Ressources> res = ressourceService.getAllRessources();
        System.out.println(res.size());
        Node[] nodes = new Node[res.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginController.class.getResource("/com/esprit/wellbeing_final/views/EmployeeUi/ressources.fxml")));
                nodes[i] = loader.load();
                RessourceController controller = loader.getController();
                controller.setInfos(res.get(i));
                resRendrer.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
