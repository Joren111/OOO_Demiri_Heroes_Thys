package domain.controller;

import java.util.ArrayList;
import java.util.List;

import domain.Question;
import domain.db.BadDb;
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
	private int count;
	
	public TestController(MessagePane messagePane){
		this.questions = BadDb.getInstance().getQuestionList();
		this.selectedAnswers = new ArrayList<String>();
		this.messagePane = messagePane;
	}
	
	public void handleTestAction() {
		this.count = 0;
		doTest(count);
	}
	
	public void handleSubmitAction() {
		this.selectedAnswers.add(this.testPane.getAsnwer());
		this.newTestWindow.close();
		count++;
		doTest(count);
	}
	
	public void doTest(int x){
		this.testPane = new TestPane(x, this.questions.get(x), this);
		Scene newTestScene = new Scene(testPane, 750, 300);
		this.newTestWindow = new Stage();
		this.newTestWindow.setScene(newTestScene);
		this.newTestWindow.show();
	}
	
	public void addSelectedAnswer(String answer){
		this.selectedAnswers.add(answer);
	}
	
	public String correct(){
		int score = 0, total=0;
		String correct, answer, resultaat = "";
		for(int i=0; i < questions.size(); i++){
			correct = this.questions.get(i).getCorrectAnswer();
			answer = this.selectedAnswers.get(i);
			total++;
			if(answer.equals(correct)){
				score++;
			}
		}
		resultaat = score + "/" + total;
		return resultaat;
	}
}
