package domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category {
	private StringProperty title, description;
	
	public void setTitle(String x){
		titleProperty().set(x);
		
	}

	public String getTitle(){
		return titleProperty().get();
	}
	
	public StringProperty titleProperty(){
		if(title == null) title = new SimpleStringProperty(this, "Title");
		return title;
	}
	
	public void setDescription(String x){
		descriptionProperty().set(x);
	}
	
	public String getDescription(){
		return descriptionProperty().get();
	}
	
	public StringProperty descriptionProperty(){
		if(description == null) description = new SimpleStringProperty(this, "Description");
		return description;
	}

}
