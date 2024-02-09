package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.Commentaire;
import com.esprit.wellbeing_final.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService {

    private Connection connection;

    // Constructor that takes a database connection
    public CommentaireService() {
        connection = MyConnection.getInstance().getCnx();;
    }

    // new commentaire
    public void addCommentaire(Commentaire commentaire) {
        String query = "INSERT INTO commentaire ( Contenu, ID_utilisateur, ID_sujet, date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, commentaire.getContenu());
            statement.setInt(2, commentaire.getID_utilisateur_commentateur());
            statement.setInt(3, commentaire.getID_sujet_associe());
            statement.setDate(4, commentaire.getDate_commentaire());

            statement.executeUpdate();
            System.out.println("Done");
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    // update
    public void updateCommentaire(Commentaire commentaire) {
        String query = "UPDATE commentaire SET Contenu=?, ID_utilisateur=?, ID_sujet=?, date=? WHERE ID=?";

        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, commentaire.getContenu());
            statement.setInt(2, commentaire.getID_utilisateur_commentateur());
            statement.setInt(3, commentaire.getID_sujet_associe());
            statement.setDate(4, commentaire.getDate_commentaire());
            statement.setInt(5, commentaire.getID_commentaire());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get commentaire by sujet
    public List<Commentaire> getAllCommentairesForSujet(int sujetID) {
        List<Commentaire> commentaires = new ArrayList<>();
        String query = "SELECT * FROM commentaire WHERE ID_sujet=?";

        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, sujetID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idCommentaire = resultSet.getInt("ID");
                    String contenu = resultSet.getString("Contenu");
                    int idUtilisateur = resultSet.getInt("ID_utilisateur");
                    Date dateCommentaire = resultSet.getDate("date");

                    Commentaire commentaire = new Commentaire(idCommentaire, contenu, idUtilisateur, sujetID, dateCommentaire);
                    commentaires.add(commentaire);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return commentaires;
    }

    //  delete
    public void deleteCommentaire(int commentaireID) {
        String query = "DELETE FROM commentaire WHERE ID=?";

        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, commentaireID);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }
}
