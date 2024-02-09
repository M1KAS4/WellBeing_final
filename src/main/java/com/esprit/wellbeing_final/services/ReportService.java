package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.tools.MyConnection;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportService {

    public boolean generateUserReport() {
        // Retrieve user data from the database
        String query = "SELECT id_user, firstName, lastName, email FROM users";
        try (
             PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Create a FileWriter to write the report to a text file
            FileWriter writer = new FileWriter("user_report.txt");

            // Write report headers
            writer.write("User Report\n\n");
            writer.write("ID_User\tFirst Name\tLast Name\tEmail\n");

            // Iterate over the result set and write each user's data to the report
            while (resultSet.next()) {
                int id = resultSet.getInt("id_user");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                //String registrationDate = resultSet.getString("registrationDate");

                // Write user data to the report
                writer.write(id + "\t" + firstName + "\t" + lastName + "\t" + email +  "\n");
            }

            // Close the FileWriter
            writer.close();

            System.out.println("User report generated successfully.");
            return true;

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., log error, display error message)
        }
        return false;
    }
}
