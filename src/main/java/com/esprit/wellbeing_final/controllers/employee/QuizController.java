package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.controllers.auth.LoginController;
import com.esprit.wellbeing_final.entities.Question;
import com.esprit.wellbeing_final.entities.QuestionsAnswer;
import com.esprit.wellbeing_final.entities.User;
import com.esprit.wellbeing_final.services.QuestionAnswerService;
import com.esprit.wellbeing_final.services.QuestionAnswersImpl;
import javafx.fxml.FXML;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

public class QuizController {

    @FXML
    ToggleButton startBtn;
    @FXML
    Pane qstPane;
    @FXML
    Label textGen;
    @FXML
    Label textGen1;
    @FXML
    Label score;
    @FXML
    Label questionLabel;
    @FXML
    VBox answersRendrer;
    @FXML
    ImageView imgbg ;
    @FXML
    ImageView scImg ;
    @FXML
    Label scLabel;
    @FXML
    private Label nomLabel;
    @FXML
    Label scsLabel;
    @FXML
    Pane scPane;
    @FXML
    Label freq;
    private ToggleGroup toggleGroup = new ToggleGroup();
    List<String> answers = new ArrayList<>();

    HashMap<String, Integer> test = new HashMap<String, Integer>();

    private Long scoreCalc = 0L;

    private int nbrQst;

    QuestionAnswerService questionAnswerService = new QuestionAnswersImpl();
    List<Question> ls = questionAnswerService.getQuestions();

    public void start() throws IOException {
        test.put("Workload", 4);
        test.put("Social Support", 3);
        test.put("Work-Life Balance", 2);
        test.put("Autonomy at Work", 4);
        test.put("Recognition and Rewards", 3);
        test.put("Communication and Feedback", 4);
        test.put("Career Growth Opportunities", 3);
        test.put("Job Security", 2);
        test.put("Workplace Environment", 4);
        test.put("Coping Mechanisms", 3);
        startBtn.setVisible(false);
        textGen.setVisible(false);
        textGen1.setVisible(false);
        imgbg.setVisible(false);
        qstPane.setVisible(true);

        nbrQst = 0;
        renderAnswerspn(nbrQst);
    }

    public void next() {
        // Check if there's a selected radio button
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            // Increment the current question index
            nbrQst++;

            // Calculate the score

            scoreCalc += (long) test.get(ls.get(nbrQst - 1).getCategory()) * questionAnswerService.getAnswerByText(selectedRadioButton.getText(), ls.get(nbrQst - 1).getId_question()).getCoef();

            // Clear the UI content
            answersRendrer.getChildren().clear();

            // Check if there are more questions
            if (nbrQst < ls.size()) {
                // Render the next question
                renderAnswerspn(nbrQst);
            } else {
                // No more questions, show final result
                scPane.setVisible(true);
                updateScoreLabel();
                System.out.println("Quiz completed");
                score.setText(String.valueOf(scoreCalc));
                sendMail(score.getText());
            }
        } else {
            System.out.println("No radio button selected");
        }
    }

    public void updateScoreLabel() {
        if (scoreCalc < 30) {
            freq.setText("You're as healthy as you can be but you're free to check the exercices tho.");
        } else if (scoreCalc >= 30 && scoreCalc < 60) {
            freq.setText("Oh ! I see you're a little stressful there my friend. There are exercices to help you ");
        } else if (scoreCalc >= 60 && scoreCalc < 80) {
            freq.setText("My dear employee, stop what you're doing and get relaxed or contact a coach if you need to talk");
        } else {
            freq.setText("Yeah you're getting an email from one of our coaches!");
        }
    }

    
    public void previous() {
        // Check if there are previous questions
        if (nbrQst > 0) {
            nbrQst--; // Decrement the current question index

            // Clear the UI content
            answersRendrer.getChildren().clear();

            // Render the previous question
            renderAnswerspn(nbrQst);
        }
    }


    public void renderAnswerspn(int p) {
        // Check if p is within the bounds of the questions list
        if (p >= 0 && p < ls.size()) {
            questionLabel.setText(ls.get(p).getQuestiontext());
            List<QuestionsAnswer> qst = questionAnswerService.getAnswersById(ls.get(p).getId_question());
            Node[] nodes = new Node[qst.size()];
            for (int i = 0; i < nodes.length; i++) { //
                try {
                    final int j = i;
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginController.class.getResource("/com/esprit/wellbeing_final/views/EmployeeUi/answer.fxml")));
                    nodes[i] = loader.load();
                    AnswerController controller = loader.getController();
                    controller.initialize(toggleGroup);
                    controller.setAnswer(qst.get(i).getAnswerText());
                    answersRendrer.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Index out of bounds: " + p);
        }
    }
    private User currentUser;
    public void setUserData(User user) {
        currentUser = user;
        nomLabel.setText(user.getFirstName() + " " + user.getLastName());
    }

    public void sendMail(String score) {

        final String username = "mariembnh.contact@gmail.com";
        final String password = "hcbo ehwe slls sodb";

        String toEmail = "2112001m@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Quizz Completion");

            String htmlContent = generateHtmlContent(score);

            message.setContent(htmlContent, "text/html");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    private String generateHtmlContent(String userScore) {
        return String.format("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Quiz Completion</title>
                <style>
                    body {
                        font-family: 'Arial', sans-serif;
                        margin: 0;
                        padding: 0;
                        background-color: #f4f4f4;
                        color: #333333;
                    }

                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: #ffffff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    }

                    h1 {
                        color: #007BFF;
                        border-bottom: 2px solid #007BFF;
                        padding-bottom: 10px;
                    }

                    p {
                        color: #555555;
                        line-height: 1.6;
                    }

                    .button {
                        display: inline-block;
                        padding: 10px 20px;
                        margin-top: 20px;
                        background-color: #007BFF;
                        color: #ffffff;
                        text-decoration: none;
                        border-radius: 5px;
                    }

                    .footer {
                        margin-top: 20px;
                        padding-top: 10px;
                        border-top: 1px solid #dddddd;
                        font-size: 14px;
                        color: #888888;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Quiz Completion</h1>
                    <p>Dear %s,</p>
                    <p>Congratulations on completing the quiz!</p>
                    <p>Your score: %s</p>
                    <p>Thank you for participating.</p>


                    <div class="footer">
                        <p>Best regards,<br>Your Quiz Team</p>
                    </div>
                </div>
            </body>
            </html>
            """, this.currentUser.getFirstName()+" "+this.currentUser.getLastName(), userScore);
    }

}
