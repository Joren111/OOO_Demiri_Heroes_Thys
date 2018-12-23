package view.panels;

import domain.db.BadDb;
import domain.model.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.plaf.ActionMapUIResource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionDetailPane extends GridPane {
	private Button btnOK, btnCancel,btnError;
	private TextArea statementsArea;
	private List<String> statementList = new ArrayList<>();
	private TextField questionField, statementField, feedbackField;
	private Button btnAdd, btnRemove;
	private ComboBox categoryField;

	private ObservableList<String> answers;

	public QuestionDetailPane() {
		answers = FXCollections.observableArrayList();
		this.setPrefHeight(300);
		this.setPrefWidth(320);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
		add(new Label("Question: "), 0, 0, 1, 1);
		questionField = new TextField();
		add(questionField, 1, 0, 2, 1);
		
		add(new Label("Statement: "), 0, 1, 1, 1);
		statementField = new TextField();
		add(statementField, 1, 1, 2, 1);

		add(new Label("Statements: "), 0, 2, 1, 1);
		statementsArea = new TextArea();
		statementsArea.setPrefRowCount(5);
		statementsArea.setEditable(false);
		add(statementsArea, 1, 2, 2, 5);

		Pane addRemove = new HBox();
		btnAdd = new Button("add");
		btnAdd.setOnAction(this::handleAddButtonAction);
		addRemove.getChildren().add(btnAdd);

		btnRemove = new Button("remove");
		btnRemove.setOnAction(this::handleRemoveButtonAction);
		addRemove.getChildren().add(btnRemove);
		add(addRemove, 1, 8, 2, 1);

		add(new Label("Category: "), 0, 9, 1, 1);
		categoryField = new ComboBox();
		categoryField.setItems(BadDb.getInstance().getCategoryList());
		add(categoryField, 1, 9, 2, 1);

		add(new Label("Feedback: "), 0, 10, 1, 1);
		feedbackField = new TextField();
		add(feedbackField, 1, 10, 2, 1);

		btnCancel = new Button("Cancel");
		btnCancel.setText("Cancel");
		setCancelAction(this::handleCancleButtonAction);
		add(btnCancel, 0, 11, 1, 1);

		btnOK = new Button("Save");
		btnOK.isDefaultButton();
		btnOK.setText("Save");
		setSaveAction(this::handleSaveButtonAction);
		add(btnOK, 1, 11, 2, 1);
	}

	public void setSaveAction(EventHandler<ActionEvent> saveAction) {
		btnOK.setOnAction(saveAction);
	}

	public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
		btnCancel.setOnAction(cancelAction);
	}

	public void setAddAction(EventHandler<ActionEvent> addAction){
		btnAdd.setOnAction(addAction);
	}

	public void setRemoveAction(EventHandler<ActionEvent> removeAction){
		btnRemove.setOnAction(removeAction);
	}

	public void setErrorOkAction(EventHandler<ActionEvent> errorOkAction) { btnError.setOnAction(errorOkAction);}

	public void handleAddButtonAction(ActionEvent event){
		if(!statementField.getText().equals("")) {
			statementList.add(statementField.getText());
			String statementsAreaText = statementsArea.getText();
			for (String s : statementList) {
				statementField.setText("");
				statementsArea.setText(statementsAreaText + s + "\n");
			}
		}
	}

	public void handleRemoveButtonAction(ActionEvent event){
		String deleteString = statementField.getText();
		if(!deleteString.equals("")) {
			Iterator<String> iter = statementList.iterator();

			while (iter.hasNext()) {
				String s = iter.next();

				if (s.equals(deleteString)) {
					iter.remove();
				}
			}
			for (String s : statementList) {
				System.out.println(s);
				statementField.setText("");
				statementsArea.setText(s + "\n");
			}
		}
	}

	public void handleSaveButtonAction(ActionEvent event){
		Question question = new Question();
		question.setQuestion(questionField.getText());

		question.setCategory(String.valueOf(categoryField.getValue()).trim());

		try{
			question.setCorrectAnswer(statementList.get(0));
		} catch (IndexOutOfBoundsException e){
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);

			btnError = new Button("Ok");
			VBox vbox;
			vbox = new VBox(new Text("No statements where given"), btnError);
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(30));

			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
			setErrorOkAction(this::handleErrorButtonAction);
		}

		Iterator<String> iter = statementList.iterator();
		while(iter.hasNext()){
			answers.add(iter.next());
		}
		question.setAnswers(answers);
		question.setFeedback(feedbackField.getText());
		BadDb.getInstance().getQuestionList().add(question);
		Stage stage = (Stage) getScene().getWindow();
		stage.close();
	}

	public void handleCancleButtonAction(ActionEvent event){
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

	public void handleErrorButtonAction(ActionEvent event){
		Stage stage = (Stage) btnError.getScene().getWindow();
		stage.close();
	}
}
