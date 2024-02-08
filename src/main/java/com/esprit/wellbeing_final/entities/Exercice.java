package com.esprit.wellbeing_final.entities;

public class Exercice {

    private String title;
    private String description;
    private String duration;
    private String type;
    private String link;

    public Exercice(String title, String description, String duration, String type, String link) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.type = type;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
