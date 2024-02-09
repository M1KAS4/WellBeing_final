package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.Role;
import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImp implements UserService {
    private User currentUser;

    @Override
    public User login(String email, String password) {
        String selectQuery = "SELECT * FROM users WHERE email = ? and password = ?";
        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long userID = resultSet.getLong("id_user");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    Role role = Role.valueOf(resultSet.getString("role"));
                    currentUser = new User(userID, firstName, lastName, email, password, role);
                    return currentUser;
                } else {
                    System.out.println("User not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException (log error, throw RuntimeException, etc.)
            throw new RuntimeException(e);
        }

        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }


    @Override
    public void createUser(User user) {
        String insertQuery = "INSERT INTO users (firstName, lastName, email, password, role) VALUES (?, ?,?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(insertQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().toString());

            preparedStatement.executeUpdate();

            System.out.println("User created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(selectAllQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long userID = resultSet.getLong("id_user");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Role role = Role.valueOf(resultSet.getString("role"));

                User user = new User(userID, firstName, lastName, email, password, role);
                userList.add(user);

                // Print out the retrieved user details
                System.out.println("User ID: " + userID + ", First Name: " + firstName + ", Last Name: " + lastName +
                        ", Email: " + email + ", Password: " + password + ", Role: " + role);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return userList;
    }
    @Override
    public void updateUser(User user) {
        String updateQuery = "UPDATE users SET firstName = ?, lastName = ?, email = ?, password = ?, role = ? WHERE id_user = ?";

        try (
                PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(updateQuery)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().toString());
            preparedStatement.setLong(6, user.getId()); // Assuming getId() returns the user's ID

            preparedStatement.executeUpdate();

            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(long userId) {
        String deleteQuery = "DELETE FROM users WHERE id_user = ?";

        try (
                PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(deleteQuery)) {

            preparedStatement.setLong(1, userId);

            preparedStatement.executeUpdate();

            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public List<User> searchUsers(String searchTerm) {
        List<User> searchResults = new ArrayList<>();
        List<User> allUsers= getAllUsers();
        // Loop through all users and check if any field contains the search term
        for (User user : allUsers) {
            if (user.getFirstName().toLowerCase().contains(searchTerm.toLowerCase())
                    || user.getLastName().toLowerCase().contains(searchTerm.toLowerCase())
                    || user.getEmail().toLowerCase().contains(searchTerm.toLowerCase()))
            {
                searchResults.add(user);
            }
        }

        // If no exact matches found, search by first letter of first name
        if (searchResults.isEmpty()) {
            for (User user : allUsers) {
                if (user.getFirstName().toLowerCase().startsWith(searchTerm.toLowerCase())) {
                    searchResults.add(user);
                }
            }
        }

        return searchResults;
    }
}
