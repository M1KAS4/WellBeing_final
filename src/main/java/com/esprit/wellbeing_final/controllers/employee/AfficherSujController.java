package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.controllers.auth.LoginController;
import com.esprit.wellbeing_final.entities.Role;
import com.esprit.wellbeing_final.entities.Sujet;
import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.services.SujetService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AfficherSujController {
    @FXML
    private Button addpub;

    @FXML
    private VBox sujetVBox;

    @FXML
    private Label chay;
    @FXML

    AnchorPane rendrer;
    @FXML
    public ScrollPane scrollPane;
    Role role = User.getSession().getRole();
    public static Sujet selectedSujet;


    @FXML
    private void initialize() {

        if (role.equals(Role.EMPLOYEE)) {
            // Hide the button for users with the "employee" role
            addpub.setVisible(false);
        } else {
            //Show the button for other roles
           addpub.setVisible(true);
        }
        // Load subjects from the database and display them in labels
        loadSujets();

        // Adjust ScrollPane to fit the whole page
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(sujetVBox);
    }

    public void loadSujets() {
        sujetVBox.getChildren().clear(); // Clear previous content

        SujetService sujetService = new SujetService();
        List<Sujet> sujets = sujetService.getAllSujets();

        for (Sujet sujet : sujets) {
            // Create VBox for each sujet
            VBox subjectVBox = new VBox(15);  // Add spacing between elements
            // Add hover event handlers to handle mouse hover events
            subjectVBox.setOnMouseEntered(event -> {
                // Store the selected sujet when hovered over
                selectedSujet = sujet;
            });
            // subjectVBox.setOnMouseExited(event -> {
            // Clear the selected sujet when cursor exits
            // selectedSujet = null;
            // });

            // Create labels for each attribute
            Label titleLabel = new Label("Title: " + sujet.getTitre());
            Label contentLabel = new Label("Content: " + sujet.getContenu());
            Label timeLabel = new Label("Time: " + sujet.getDate_creation_sujet());
            Label ownerLabel = new Label("Owner: " + User.getSession().getFirstName() + " " + User.getSession().getLastName());

            // Create delete button
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                // Show a confirmation dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete");
                alert.setContentText("Are you sure you want to delete this subject? This action is irreversible");

                // Add buttons to the dialog
                ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(deleteButtonType, cancelButtonType);

                // Handle the result of the dialog
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == deleteButtonType) {
                    // If user clicks Delete, proceed with deletion
                    sujetService.deleteSujet(sujet.getID_sujet()); // Example of how to delete the sujet
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Subject Deleted");
                    al.setContentText("Subject Deleted Successfully !");
                    al.show();
                    loadSujets(); // Reload sujets after deletion
                }
            });

            // Create update button
            Button updateButton = new Button("Update");
            updateButton.setOnAction(event -> {
                try {
                    // Load the UpdateSujet.fxml file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/wellbeing_final/views/UpdateSujet.fxml"));
                    Parent root = loader.load();

                    // Get the controller associated with the loaded FXML file
                    UpdateSujetController updateController = loader.getController();

                    // Pass the selected sujet to the update controller
                    updateController.setSujetToUpdate(selectedSujet);

                    // Display the UpdateSujet.fxml file
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Add labels and buttons to the subjectVBox
            subjectVBox.getChildren().addAll(titleLabel, contentLabel, timeLabel, ownerLabel, deleteButton, updateButton);

            // Add subjectVBox to sujetVBox
            sujetVBox.getChildren().add(subjectVBox);
        }
    }


    @FXML


    public void ajouterpub() throws IOException {
        rendrer.getChildren().removeAll();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginController.class.getResource("/com/esprit/wellbeing_final/views/ajoutersujet.fxml")));
        Node menu = loader.load();
        rendrer.getChildren().setAll(menu);

    }

    public static Sujet getSelectedSujet() {
        return selectedSujet;
    }

    private void openUpdateControllerWithSelectedSujet(Sujet sujet) {
        try {
            // Load the UpdateSujet.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/wellbeing_final/views/UpdateSujet.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the loaded FXML file
            UpdateSujetController updateController = loader.getController();

            // Pass the selected sujet to the update controller
            updateController.setSujetToUpdate(sujet);

            // Display the UpdateSujet.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
