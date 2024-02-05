package com.esprit.wellbeing_final.controllers.employee;

import com.esprit.wellbeing_final.controllers.auth.LoginController;
import com.esprit.wellbeing_final.entities.Question;
import com.esprit.wellbeing_final.entities.QuestionsAnswer;
import com.esprit.wellbeing_final.services.QuestionAnswerService;
import com.esprit.wellbeing_final.services.QuestionAnswersImpl;
import javafx.fxml.FXML;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    ImageView imgbg;
    private ToggleGroup toggleGroup = new ToggleGroup();
    List<String> answers = new ArrayList<>();

    HashMap<String,Integer> test = new HashMap<String,Integer>();

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
        test.put("Color", 3);
        test.put("Fitness", 5);
        test.put("Technology", 8);
        startBtn.setVisible(false);
        textGen.setVisible(false);
        textGen1.setVisible(false);
        imgbg.setVisible(false);
        qstPane.setVisible(true);

        nbrQst = 0;
        renderAnswerspn(nbrQst);
    }

    public void next(){
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            System.out.println(test.get(ls.get(nbrQst-1).getCategory()));

            scoreCalc += (long) test.get(ls.get(nbrQst-1).getCategory()) * questionAnswerService.getAnswerByText(selectedRadioButton.getText(),ls.get(nbrQst-1).getId_question()).getCoef();
            answersRendrer.getChildren().clear();
            if (nbrQst < ls.size()) {
                renderAnswerspn(nbrQst);
            }else{
                startBtn.setVisible(true);
                textGen.setVisible(true);
                qstPane.setVisible(false);
                System.out.println("wfe");
                score.setText(String.valueOf(scoreCalc));
            }
        } else {
            System.out.println("dhhdhdh");
        }
    }

    public void renderAnswerspn(int p){
        questionLabel.setText(ls.get(p).getQuestiontext());
        List<QuestionsAnswer> qst = questionAnswerService.getAnswersById(ls.get(p).getId_question());
        Node[] nodes = new Node[qst.size()];
        for (int i = 0; i < nodes.length; i++) {
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
        nbrQst ++;
    }


}
