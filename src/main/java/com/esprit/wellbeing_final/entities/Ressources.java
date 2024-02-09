package com.esprit.wellbeing_final.entities;

public class Ressources {
    private int idRessource;
    private String titre;
    private String description;
    private String lien;
    private Integer views;

    public Ressources(int idRessource, String titre, String description, String lien, Integer views) {
        this.idRessource = idRessource;
        this.titre = titre;
        this.description = description;
        this.lien = lien;
        this.views = views;
    }

    public Ressources() {

    }

    // Getters
    public int getIdRessource() {
        return idRessource;
    }

    // Setters
    public void setIdRessource(int idRessource) {
        this.idRessource = idRessource;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

}



