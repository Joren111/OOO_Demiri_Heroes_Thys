package view.panels;

import controller.TestController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestPane extends GridPane {
    private Label questionField;
    private Button submitButton;
    private ToggleGroup statementGroup;
    private int x;
    private Question question;
    private TestController testController;

    public TestPane(Question question, TestController testController) {
        List<String> answers = new ArrayList<>();

        // Deep copy is required here
        for (String answer : question.getAnswers())
            answers.add(answer);

        Collections.shuffle(answers);

        this.setPrefHeight(300);
        this.setPrefWidth(750);

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        int a = 1;
        this.question = question;
        this.testController = testController;

        questionField = new Label(this.question.getQuestion());
        add(questionField, 0, 0, 1, 1);

        statementGroup = new ToggleGroup();
        for (int i = 0; i < answers.size(); i++) {
            RadioButton rb1 = new RadioButton(answers.get(i));
            rb1.setToggleGroup(statementGroup);
            add(rb1, 0, i + 1, 1, 1);
            a++;
        }

        submitButton = new Button("Submit");
        submitButton.setOnAction(this::handleSubmitAction);
        add(submitButton, 0, a + 1, 1, 1);
    }

    private void handleSubmitAction(ActionEvent event) {
        this.testController.handleSubmitAction();
    }

    public String getAsnwer() {
        String answer = null;
        if (statementGroup.getSelectedToggle() != null) {
            answer = ((RadioButton) statementGroup.getSelectedToggle()).getText();
        } else {
            answer = "0";
        }
        return answer;
    }

    public List<String> getSelectedStatements() {
        List<String> selected = new ArrayList<String>();
        if (statementGroup.getSelectedToggle() != null) {
            selected.add(statementGroup.getSelectedToggle().getUserData().toString());
        }
        return selected;
    }
}
