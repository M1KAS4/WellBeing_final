package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.Exercice;
import com.esprit.wellbeing_final.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ExercicesImpl implements ExercicesService{
    @Override
    public List<Exercice> getExercicesByCategory(String cat) {
        List<Exercice> exercices = new ArrayList<>();

        try {

            // Query to get exercises by category
            String sql = "SELECT * FROM exercice WHERE category = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(sql);

            preparedStatement.setString(1, cat);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String duration = resultSet.getString("duration");
                String category = resultSet.getString("category");
                String lien = resultSet.getString("lien");

                Exercice exercice = new Exercice( title, description, duration, category, lien);
                exercices.add(exercice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercices;
    }
}
