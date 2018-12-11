package domain.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import domain.Category;
import domain.Question;
import domain.db.BadDb;
import domain.model.Correct;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.panels.MessagePane;
import view.panels.TestPane;

public class TestController {
	private MessagePane messagePane;
	private TestPane testPane;
	private List<String> selectedAnswers;
	private List<Question> questions;
	private Stage newTestWindow;
	private Correct correct;
	private int count;
	
	public TestController(MessagePane messagePane, Correct correct){
		this.questions = BadDb.getInstance().getQuestionList();
		this.selectedAnswers = new ArrayList<String>();
		this.messagePane = messagePane;
		this.correct = correct;
	}
	
	public void handleTestAction() {
		this.count = 0;
		doTest();
	}
	
	public void handleSubmitAction() {
		this.selectedAnswers.add(this.testPane.getAsnwer());
		this.newTestWindow.close();
		this.count++;
		doTest();
	}
	
	public void doTest(){
		if(this.count < this.questions.size()){
			this.testPane = new TestPane(this.count, this.questions.get(this.count), this);
			Scene newTestScene = new Scene(testPane, 750, 300);
			this.newTestWindow = new Stage();
			this.newTestWindow.setScene(newTestScene);
			this.newTestWindow.show();
		}
		else{
			this.messagePane.addScore(this.correct.correctPerCategory(this.selectedAnswers));
		}
	}
	
	public void addSelectedAnswer(String answer){
		this.selectedAnswers.add(answer);
	}
}
