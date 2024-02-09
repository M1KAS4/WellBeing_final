package com.esprit.wellbeing_final.entities;

import java.sql.Date;

public class Sujet {
    private int ID_sujet;
    private String Titre;
    private String Contenu;
    private Long ID_utilisateur_auteur;
    private Date  Date_creation_sujet;

    public Sujet(int ID_sujet, String titre, String contenu, Long ID_utilisateur_auteur, Date date_creation_sujet) {
        this.ID_sujet = ID_sujet;
        Titre = titre;
        Contenu = contenu;
        this.ID_utilisateur_auteur = ID_utilisateur_auteur;
        Date_creation_sujet = date_creation_sujet;
    }

    public Sujet() {
    }

    public int getID_sujet() {
        return ID_sujet;
    }

    public void setID_sujet(int ID_sujet) {
        this.ID_sujet = ID_sujet;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setContenu(String contenu) {
        Contenu = contenu;
    }

    public Long getID_utilisateur_auteur() {
        return ID_utilisateur_auteur;
    }

    public void setID_utilisateur_auteur(Long ID_utilisateur_auteur) {
        this.ID_utilisateur_auteur = ID_utilisateur_auteur;
    }

    public Date getDate_creation_sujet() {
        return Date_creation_sujet;
    }

    public void setDate_creation_sujet(Date date_creation_sujet) {
        Date_creation_sujet = date_creation_sujet;
    }

    @Override
    public String toString() {
        return "Sujet{" +
                "ID_sujet=" + ID_sujet +
                ", Titre='" + Titre + '\'' +
                ", Contenu='" + Contenu + '\'' +
                ", ID_utilisateur_auteur=" + ID_utilisateur_auteur +
                ", Date_creation_sujet=" + Date_creation_sujet +
                '}';
    }
}
