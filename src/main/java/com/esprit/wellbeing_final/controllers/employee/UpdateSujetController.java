package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.entities.Sujet;
import com.esprit.wellbeing_final.services.SujetService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

import static com.esprit.wellbeing_final.controllers.employee.AfficherSujController.selectedSujet;

public class UpdateSujetController {

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField contenuTextField;

    @FXML
    private Label titre;

    @FXML
    private Button updatebtn;
    LocalDate currentDate = LocalDate.now();
    Date sqldate = Date.valueOf(currentDate);
    private Sujet sujetToUpdate;

    @FXML
    private void handleUpdateButton() {
        if (selectedSujet == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No sujet selected for update.");
            return;
        }

        String title = titleTextField.getText();
        String contenu = contenuTextField.getText();

        // Mettre à jour l'objet sujet avec les nouvelles valeurs
        selectedSujet.setTitre(title);
        selectedSujet.setContenu(contenu);

        SujetService ss = new SujetService();

        try {
            ss.updateSujet(selectedSujet);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Subject updated successfully!");
          //  loadSujets(); // Recharger les sujets après la mise à jour
        } catch (Exception e) {
            e.printStackTrace(); // Enregistrer l'exception si nécessaire
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the subject.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setSujetToUpdate(Sujet sujet) {
        this.sujetToUpdate = sujet;
    }
}


