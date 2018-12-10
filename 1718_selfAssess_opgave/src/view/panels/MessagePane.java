package view.panels;

import java.util.ArrayList;
import java.util.List;

import domain.Question;
import domain.controller.TestController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        
       	scoreField = new Label("Score=0/0");
        add(scoreField, 0,1,1,1);
        
		testButton = new Button("Evaluate");
		testButton.setOnAction(this::handleTestAction);
		add(testButton, 0,2,1,1);
		setHalignment(testButton, HPos.CENTER);
	}
	
	public void handleTestAction(ActionEvent event){
		this.testController.handleTestAction();
	}
	
	public void addTestController(TestController testController){
		this.testController = testController;
	}
}
