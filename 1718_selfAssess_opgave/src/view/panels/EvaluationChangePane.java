package view.panels;

import db.PropertyStrategy;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;


public class EvaluationChangePane extends GridPane {
    private PropertyStrategy prop;
    private Button evaluationModeButton,btnOk;
    private boolean close = false;

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

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);

        btnOk = new Button("Ok");
        VBox vbox;
        vbox = new VBox(new Text("Restart application neccesary for changes"), btnOk);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));

        dialogStage.setScene(new Scene(vbox));
        dialogStage.show();
        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }
}
