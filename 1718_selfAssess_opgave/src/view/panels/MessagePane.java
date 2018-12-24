package view.panels;

import controller.TestController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.List;

public class MessagePane extends GridPane {
	private Label scoreField;
	private Button testButton;
	private TestController testController;
	
	public MessagePane (){
	    setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        scoreField = new Label();
        scoreField.setText("You never did this evaluation");
        this.add(scoreField, 0,5,1,1);

		testButton = new Button("Evaluate");
		testButton.setOnAction(this::handleTestAction);
		this.add(testButton, 0,10,1,1);
		setHalignment(testButton, HPos.CENTER);
	}
	
	public void handleTestAction(ActionEvent event){
		scoreField.setText("");
		this.testController.handleTestAction();
	}

	public void addScore(List<String> resultaat){
		scoreField.setText("");
		for(int i = 0; i < resultaat.size(); i++){
			String temp = scoreField.getText();
			scoreField.setText(temp + resultaat.get(i) + "\n");
		}	
	}
	
	public void addTestController(TestController testController){
		this.testController = testController;
	}
}
