package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.Role;
import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.tools.MyConnection;

import java.sql.*;

public class UserServiceImp implements UserService  {
    @Override
    public User login(String email, String password) {
        String SelectQuery = "SELECT * FROM users WHERE email = ? and password= ?";
        try(PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(SelectQuery, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet str = preparedStatement.executeQuery();
            if (str.next()) {
                Long userID = str.getLong("id_user");
                String firstName = str.getString("firstName");
                String LastName = str.getString("lastName");
                Date Birthdate = str.getDate("birthdate");
                Role role = Role.valueOf(str.getString("role"));
                return new User(userID, firstName, LastName, Birthdate, email, password, role);
            } else {
                throw new SQLException("Failed");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
