package view.panels;

import db.PropertyStrategy;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class EvaluationChangePane extends GridPane {
    private PropertyStrategy prop;
    private Button evaluationModeButton;

    public EvaluationChangePane (){
        prop = new PropertyStrategy();

        evaluationModeButton = new Button("Change evaluation mode");
        setChangeEvaluationAction(this::handleEvaluationChange);
        this.add(evaluationModeButton, 0,10,1,1);
        setHalignment(evaluationModeButton, HPos.CENTER);
    }

    public void setChangeEvaluationAction(EventHandler<ActionEvent> changeAction) {
        evaluationModeButton.setOnAction(changeAction);
    }

    public void handleEvaluationChange(ActionEvent event){
        String property = (String) prop.load().get(0);

        ArrayList<String> content = new ArrayList<>();
        if(property.equals("score") ? content.add("feedback"): content.add("score"));
        prop.save(content);
    }
}
