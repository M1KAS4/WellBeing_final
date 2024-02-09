package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.Sujet;
import com.esprit.wellbeing_final.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SujetService {

    private Connection connection;

    public SujetService() {
        connection = MyConnection.getInstance().getCnx();
    }

    //  ajout
    public void addSujet(Sujet sujet) {
        String query = "INSERT INTO sujet (id_sujet, titre, contenu, date, id_user)"+" VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, sujet.getID_sujet());
            statement.setString(2, sujet.getTitre());
            statement.setString(3, sujet.getContenu());
            statement.setDate(4, sujet.getDate_creation_sujet());
            statement.setLong(5, sujet.getID_utilisateur_auteur());

            statement.executeUpdate();
            System.out.println("Done");
        }catch (SQLException ex){
            System.err.println("Error");
            System.out.println(ex);

        }
    }

    // update
    public void updateSujet(Sujet sujet)  {
        String query = "UPDATE sujet SET titre = ?, contenu = ?, date = ?, id_user = ? WHERE id_sujet = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, sujet.getTitre());
            statement.setString(2, sujet.getContenu());
            statement.setDate(3, sujet.getDate_creation_sujet());
            statement.setLong(4, sujet.getID_utilisateur_auteur());
            statement.setInt(5, sujet.getID_sujet());

            statement.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error");
            System.out.println(ex);
        }
    }

    // recuperer
    public List<Sujet> getAllSujets()  {
        List<Sujet> sujets = new ArrayList<>();
        String query = "SELECT * FROM sujet";

        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idSujet = resultSet.getInt("id_sujet");
                String titre = resultSet.getString("titre");
                String contenu = resultSet.getString("contenu");
                Date dateCreation = resultSet.getDate("date");
                Long idUser = resultSet.getLong("id_user");

                Sujet sujet = new Sujet(idSujet, titre, contenu, idUser, dateCreation);
                sujets.add(sujet);
            }
        } catch (SQLException ex){
            System.err.println("Error");
        }

        return sujets;
    }

    // delete
    public void deleteSujet(int idSujet) {
        String query = "DELETE FROM sujet WHERE id_sujet=?";

        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, idSujet);

            statement.executeUpdate();
        } catch (SQLException ex){
            System.err.println("Error");
        }
    }
}
