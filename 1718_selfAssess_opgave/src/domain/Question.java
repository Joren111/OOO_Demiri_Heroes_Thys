package domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Question {
private StringProperty question, category;
	
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
}
