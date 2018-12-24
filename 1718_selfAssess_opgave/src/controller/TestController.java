package controller;

import java.util.ArrayList;
import java.util.List;

import db.PropertyStrategy;
import model.Question;
import db.BadDb;
import model.Correct;
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

	private PropertyStrategy prop;
	
	public TestController(MessagePane messagePane, Correct correct){
		this.questions = BadDb.getInstance().getQuestionList();

		this.messagePane = messagePane;
		this.correct = correct;
		this.prop = new PropertyStrategy();
	}
	
	public void handleTestAction() {
		this.count = 0;
		this.selectedAnswers = new ArrayList<String>();
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
			String property = (String) prop.load().get(0);
			if(property.equals("score")){
				this.messagePane.addScore(this.correct.correctPerCategory(this.selectedAnswers));
			}else{
				this.messagePane.addScore(this.correct.feedback(this.selectedAnswers));
			}
		}
	}
	
	public void addSelectedAnswer(String answer){
		this.selectedAnswers.add(answer);
	}
}
