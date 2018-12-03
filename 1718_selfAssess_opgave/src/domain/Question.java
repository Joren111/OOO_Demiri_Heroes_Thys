package domain;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Question {
private StringProperty question, category, correctAnswer;
private ListProperty answers;
	
	public void setQuestion(String x){
		questionProperty().set(x);
		
	}

	public String getQuestion(){
		return questionProperty().get();
	}
	
	public StringProperty questionProperty(){
		if(question == null) question = new SimpleStringProperty(this, "Question");
		return question;
	}
	
	public void setCategory(String x){
		categoryProperty().set(x);
	}
	
	public String getCategory(){
		return categoryProperty().get();
	}
	
	public StringProperty categoryProperty(){
		if(category == null) category = new SimpleStringProperty(this, "Category");
		return category;
	}
	
	public void setCorrectAnswer(String x){
		correctAnswerProperty().set(x);
	}
	
	public String getCorrectAnswer(){
		return correctAnswerProperty().get();
	}
	
	public StringProperty correctAnswerProperty(){
		if(correctAnswer == null) correctAnswer = new SimpleStringProperty(this, "correctAnswer");
		return correctAnswer; 
	}
	
	public void setAnswers(ObservableList<String> x){
		answersProperty().set(x);
	}
	
	public ObservableList<String> getAnswers(){
		return answersProperty().get();
	}
	
	public ListProperty<String> answersProperty(){
		if(answers == null) answers = new SimpleListProperty(this, "Answers");
		return answers;
	}
}
