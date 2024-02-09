package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.entities.Sujet;
import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.services.SujetService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AjoutersujetController {

    //private static final int BAN_DURATION = 10;
    @FXML
    private TextField titleTextField;

    @FXML
    private TextField contenuTextField;

    @FXML
    private Label titre;

    @FXML
    private Button sendButton;
    LocalDate currentDate = LocalDate.now();
    Date sqldate = Date.valueOf(currentDate);

    private Map<Long, Long> profanityCountMap = new HashMap<>(); // Map to store profanity count for each user

    // This method will be called when the "Send" button is clicked
    @FXML
    private void handleSendButton() {
        Long userId = User.getSession().getId();
        if (hasProfanity()) {
            Long profanityCount = profanityCountMap.getOrDefault(userId, 0l);
            profanityCount++;
            profanityCountMap.put(userId, profanityCount);
            if (profanityCount >= 3L) {
                banUserFor10Minutes();
                sendBanEmail(User.getSession().getEmail());
                showAlert(Alert.AlertType.ERROR, "Error", "You have been banned for 10 minutes due to inappropriate language.");
                return;
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please avoid using profanity in your title or content.");
                return;
            }
        }

        profanityCountMap.put(userId, 0L); // Reset profanity count if no profanity detected

        User u = User.getSession();
        Long idowner = u.getId();
        String title = titleTextField.getText();
        String contenu = contenuTextField.getText();
        Sujet suj = new Sujet();
        suj.setContenu(contenu);
        suj.setTitre(title);
        suj.setDate_creation_sujet(sqldate);
        suj.setID_utilisateur_auteur(idowner);

        SujetService ss = new SujetService();

        try {
            ss.addSujet(suj);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Publication added successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception if needed
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the publication.");
        }
    }

    private boolean hasProfanity() {
        try {
            String encodedContenu = URLEncoder.encode(contenuTextField.getText(), "UTF-8");
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
    }


    private void banUserFor10Minutes() {


        sendButton.setDisable(true); // Assuming sendButton is a member variable representing the button
        long BAN_DURATION = 600;
        long banEndTime = System.currentTimeMillis() + BAN_DURATION * 1000;

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            long remainingTime = banEndTime - System.currentTimeMillis();
            if (remainingTime <= 0) {
                Platform.runLater(() -> {
                    sendButton.setDisable(false);
                    sendButton.setText("Ajouter un commentaire");
                });
                scheduler.shutdown();
                return;
            }
            Platform.runLater(() -> {
                sendButton.setText("Banni pour " + remainingTime / 1000 + "s");
            });
        }, 0, 1, TimeUnit.SECONDS);

        Alert banAlert = new Alert(Alert.AlertType.ERROR);
        banAlert.setTitle("Bannissement");
        banAlert.setHeaderText("Vous avez été banni pour " + BAN_DURATION + " secondes");
        banAlert.showAndWait();


    }


    private void sendBanEmail(String userEmail) {
        final String username = "sadreddine.ouertani@etudiant-isi.utm.tn"; // Votre adresse e-mail
        final String password = ""; // Votre mot de passe e-mail

        // Configuration pour l'envoi d'e-mails via Gmail SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Créer une session pour l'envoi d'e-mails
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Créer un message MIME
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Définir l'adresse e-mail de l'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail)); // Définir l'adresse e-mail du destinataire
            message.setSubject("Ban Notification"); // Définir l'objet du message
            message.setText("Dear " + User.getSession().getFirstName() + ",\n\nYou have been banned from our application for 10 minutes due to inappropriate behavior. Please refrain from such actions in the future.\n\nBest regards,\nThe Admin Team"); // Définir le contenu du message

            // Envoyer le message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
