package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.Role;
import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.tools.MyConnection;

import java.sql.*;

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
                    Date birthdate = resultSet.getDate("birthdate");
                    Role role = Role.valueOf(resultSet.getString("role"));
                    currentUser = new User(userID, firstName, lastName, birthdate, email, password, role);
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
}
