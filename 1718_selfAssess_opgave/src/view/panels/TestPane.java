package view.panels;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import domain.Question;
import domain.controller.TestController;
import domain.db.BadDb;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TestPane extends GridPane {
	private Label questionField;
	private Button submitButton;
	private ToggleGroup statementGroup;
	private int x;
	private Question question;
	private TestController testController;
	
	public TestPane (int x, Question question, TestController testController){
		this.setPrefHeight(300);
		this.setPrefWidth(750);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
        int a = 1;
        this.question = question;
        ObservableList<String> answers = this.question.getAnswers();
        this.testController = testController;
        
		questionField = new Label(this.question.getQuestion());
		add(questionField, 0, 0, 1, 1);
		
		statementGroup = new ToggleGroup();
		FXCollections.shuffle(answers);
		for(int i = 0; i < answers.size(); i++){
			RadioButton rb1 = new RadioButton(answers.get(i));
			rb1.setToggleGroup(statementGroup);
			add(rb1, 0, i+1, 1, 1);
			a++;
		}
		x++;
		this.x = x;
		submitButton = new Button("Submit");
		submitButton.setOnAction(this::handleSubmitAction);
		add(submitButton, 0, a+1, 1, 1);
	}
	
	private void handleSubmitAction(ActionEvent event){
		this.testController.handleSubmitAction();
	}

	public String getAsnwer(){
		String answer = null;
		if(statementGroup.getSelectedToggle()!=null){
			answer = ((RadioButton) statementGroup.getSelectedToggle()).getText();
		}else{
			answer = "0";
		}
		return answer;
	}

	public List<String> getSelectedStatements() {
		 List<String> selected = new ArrayList<String>();
		if(statementGroup.getSelectedToggle()!=null){
			selected.add(statementGroup.getSelectedToggle().getUserData().toString());
		}
		return selected;
	}
}
