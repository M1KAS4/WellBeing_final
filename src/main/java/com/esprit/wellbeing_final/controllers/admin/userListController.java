package com.esprit.wellbeing_final.controllers.admin;

import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.services.ReportService;
import com.esprit.wellbeing_final.services.UserServiceImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class userListController {
    @FXML
    private void handleAddUserButtonClick(ActionEvent event) {
        try {
            // Load the AddUser.fxml file
            Parent addUserParent = FXMLLoader.load(getClass().getResource("/com/esprit/wellbeing_final/views/AdminUi/AddUser.fxml"));
            Scene addUserScene = new Scene(addUserParent);

            // Get the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the AddUser.fxml scene onto the stage
            window.setScene(addUserScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, Long> idColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    private final UserServiceImp userService = new UserServiceImp();
    private final ReportService rp = new ReportService();


    @FXML
    public void initialize() {
        // Initialize the TableView columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Load users and populate TableView
        loadUsers();
    }

    private void loadUsers() {
        List<User> userList = userService.getAllUsers();
        ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
        userTableView.setItems(observableUserList);
    }
    @FXML
    private void handleSignOutButtonClick(ActionEvent event) {
        // Perform sign out actions here, such as clearing session data, etc.

        // For example, navigate back to the login screen
        try {
            Parent loginParent = FXMLLoader.load(getClass().getResource("/com/esprit/wellbeing_final/views/Login.fxml"));
            Scene loginScene = new Scene(loginParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(loginScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private TextField searchField;

    @FXML
    private void handleSearchButtonClick(ActionEvent event) {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            List<User> searchResults = userService.searchUsers(searchTerm); // Implement this method in your UserService
            ObservableList<User> observableSearchResults = FXCollections.observableArrayList(searchResults);
            userTableView.setItems(observableSearchResults);
        } else {
            // If search field is empty, reload all users
            loadUsers();
        }
    }
    @FXML
    private void handleGenerateReportButtonClick(ActionEvent event) {
        // Call the method to generate the user report
        boolean success = rp.generateUserReport();

        // Show a confirmation dialog based on the result
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Report Generated");
            alert.setHeaderText(null);
            alert.setContentText("User report generated successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to generate user report. Please try again.");
            alert.showAndWait();
        }
    }
}
