package domain.controller;

import java.util.ArrayList;
import java.util.List;

import domain.Question;
import domain.db.BadDb;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.panels.TestPane;

public class TestController {
	private List<String> selectedAnswers;
	private List<Question> questions;
	
	public TestController(){
		this.questions = BadDb.getInstance().getQuestionList();
		this.selectedAnswers = new ArrayList<String>();
	}
	
	public void doTest(int x){
		TestPane testPane = new TestPane(x, this.questions.get(x));
		Scene newTestScene = new Scene(testPane, 750, 300);
		Stage newTestWindow = new Stage();
		newTestWindow.setScene(newTestScene);
		newTestWindow.show();
		selectedAnswers.add(testPane.getAnswer());
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
