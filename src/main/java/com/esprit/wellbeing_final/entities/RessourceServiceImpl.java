package com.esprit.wellbeing_final.entities;

import com.esprit.wellbeing_final.services.RessourceService;
import com.esprit.wellbeing_final.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RessourceServiceImpl implements RessourceService {
    @Override
    public List<Ressources> getAllRessources() {
        List<Ressources> ressourceList = new ArrayList<>();

        String query = "SELECT * FROM ressource ORDER BY ressource.views DESC";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Ressources ressource = new Ressources
                        ();
                ressource.setIdRessource(resultSet.getInt("id_ressouce"));
                ressource.setTitre(resultSet.getString("title"));
                ressource.setDescription(resultSet.getString("description"));
                ressource.setLien(resultSet.getString("lien"));
                ressource.setViews(resultSet.getInt("views"));

                ressourceList.add(ressource);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ressourceList;
    }

    @Override
    public Ressources getRessourceById(int id) {
        Ressources ressource = null;

        String query = "SELECT * FROM ressource WHERE id_ressouce = ?";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ressource = new Ressources();
                    ressource.setIdRessource(resultSet.getInt("idRessource"));
                    ressource.setTitre(resultSet.getString("titre"));
                    ressource.setDescription(resultSet.getString("description"));
                    ressource.setLien(resultSet.getString("lien"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ressource;
    }

    @Override
    public void addRessource(Ressources ressource) {
        String query = "INSERT INTO ressource (titre, type, description, lien) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            preparedStatement.setString(1, ressource.getTitre());
            preparedStatement.setString(3, ressource.getDescription());
            preparedStatement.setString(4, ressource.getLien());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRessource(Ressources ressource) {
        String query = "UPDATE ressource SET titre = ?, description = ?, lien = ? WHERE id_ressouce = ?";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            preparedStatement.setString(1, ressource.getTitre());
            preparedStatement.setString(3, ressource.getDescription());
            preparedStatement.setString(4, ressource.getLien());
            preparedStatement.setInt(5, ressource.getIdRessource());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRessource(int id) {
        String query = "DELETE FROM ressource WHERE id_ressouce = ?";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incrementRessourceViews(Ressources ressource) {
        String query = "UPDATE ressource SET views = ? WHERE id_ressouce = ?";

        int views = ressource.getViews() + 1;

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            preparedStatement.setInt(1, views);
            preparedStatement.setInt(2, ressource.getIdRessource());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
