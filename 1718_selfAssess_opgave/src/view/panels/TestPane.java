package view.panels;

import java.util.ArrayList;
import java.util.List;

import domain.Question;
import domain.db.BadDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TestPane extends GridPane {
	private Label questionField;
	private Button submitButton;
	private ToggleGroup statementGroup;
	private int x;
	private ObservableList<Question> questions;
	
	public TestPane (int x){
		this.setPrefHeight(300);
		this.setPrefWidth(750);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
        int a = 1;
        this.questions = BadDb.getInstance().getQuestionList();
        ObservableList<String> answers = this.questions.get(x).getAnswers();
        
		questionField = new Label(this.questions.get(x).getQuestion());
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
		setProcessAnswerAction(this::handleSubmitAction);
		add(submitButton, 0, a+1, 1, 1);
	}

	private void handleSubmitAction(ActionEvent event){
		if(this.x < this.questions.size()){
			TestPane testPane = new TestPane(this.x);
	        Scene newTestScene = new Scene(testPane, 750, 300);
	        Stage newTestWindow = new Stage();
	        newTestWindow.setScene(newTestScene);
	        newTestWindow.show();	
		}
	}
	
	public void setProcessAnswerAction(EventHandler<ActionEvent> processAnswerAction) {
		submitButton.setOnAction(processAnswerAction);
	}

	public List<String> getSelectedStatements() {
		 List<String> selected = new ArrayList<String>();
		if(statementGroup.getSelectedToggle()!=null){
			selected.add(statementGroup.getSelectedToggle().getUserData().toString());
		}
		return selected;
	}
}
