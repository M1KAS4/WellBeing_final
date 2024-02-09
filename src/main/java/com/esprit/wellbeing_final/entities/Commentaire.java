package com.esprit.wellbeing_final.entities;

import java.sql.Date;

public class Commentaire {
    private int ID_commentaire;
    private String Contenu;
    private int ID_utilisateur_commentateur;
    private int ID_sujet_associe;
    private Date Date_commentaire;

    public Commentaire(int ID_commentaire, String contenu, int ID_utilisateur_commentateur, int ID_sujet_associe, Date date_commentaire) {
        this.ID_commentaire = ID_commentaire;
        Contenu = contenu;
        this.ID_utilisateur_commentateur = ID_utilisateur_commentateur;
        this.ID_sujet_associe = ID_sujet_associe;
        Date_commentaire = date_commentaire;
    }

    public int getID_commentaire() {
        return ID_commentaire;
    }

    public void setID_commentaire(int ID_commentaire) {
        this.ID_commentaire = ID_commentaire;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setContenu(String contenu) {
        Contenu = contenu;
    }

    public int getID_utilisateur_commentateur() {
        return ID_utilisateur_commentateur;
    }

    public void setID_utilisateur_commentateur(int ID_utilisateur_commentateur) {
        this.ID_utilisateur_commentateur = ID_utilisateur_commentateur;
    }

    public int getID_sujet_associe() {
        return ID_sujet_associe;
    }

    public void setID_sujet_associe(int ID_sujet_associe) {
        this.ID_sujet_associe = ID_sujet_associe;
    }

    public Date getDate_commentaire() {
        return Date_commentaire;
    }

    public void setDate_commentaire(Date date_commentaire) {
        Date_commentaire = date_commentaire;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "ID_commentaire=" + ID_commentaire +
                ", Contenu='" + Contenu + '\'' +
                ", ID_utilisateur_commentateur=" + ID_utilisateur_commentateur +
                ", ID_sujet_associe=" + ID_sujet_associe +
                ", Date_commentaire=" + Date_commentaire +
                '}';
    }
    /* public boolean hasProfanity() {
        try {
            String encodedContenu = URLEncoder.encode(Contenu, "UTF-8");
            URL url = new URL("https://www.purgomalum.com/service/json?text=" + encodedContenu);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
            String response = responseBuilder.toString();
            JSONObject jsonObject = new JSONObject(response);
            String result = jsonObject.getString("result");
            return result.contains("*");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }*/

}
