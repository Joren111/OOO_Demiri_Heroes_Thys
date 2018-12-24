package view.panels;

import com.sun.deploy.util.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Category;
import model.Question;
import model.db.BadDb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionDetailPane extends GridPane {
    private Button btnOK, btnCancel, btnError;
    private TextArea statementsArea;
    private List<String> statementList = new ArrayList<>();
    private TextField questionField, statementField, feedbackField;
    private Button btnAdd, btnRemove;
    private ComboBox categoryField;
    private Question questionToUpdate;

    public QuestionDetailPane(Question questionToUpdate) {
        this.questionToUpdate = questionToUpdate;

        // Deep copy is required here
        for (String statement : questionToUpdate.getAnswers())
            statementList.add(statement);

        this.setPrefHeight(300);
        this.setPrefWidth(320);

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        add(new Label("Question: "), 0, 0, 1, 1);
        questionField = new TextField();
        questionField.setText(questionToUpdate.getQuestion());
        add(questionField, 1, 0, 2, 1);

        add(new Label("Statement: "), 0, 1, 1, 1);
        statementField = new TextField();
        add(statementField, 1, 1, 2, 1);

        add(new Label("Statements: "), 0, 2, 1, 1);
        statementsArea = new TextArea();
        statementsArea.setPrefRowCount(5);
        statementsArea.setEditable(false);
        statementsArea.setText(StringUtils.join(questionToUpdate.getAnswers(), "\n"));
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
        categoryField.getSelectionModel().select(questionToUpdate.getCategory());

        add(new Label("Feedback: "), 0, 10, 1, 1);
        feedbackField = new TextField();
        feedbackField.setText(questionToUpdate.getFeedback());
        add(feedbackField, 1, 10, 2, 1);

        btnCancel = new Button("Cancel");
        btnCancel.setText("Cancel");
        setCancelAction(this::handleCancelButtonAction);
        add(btnCancel, 0, 11, 1, 1);

        btnOK = new Button("Save");
        btnOK.isDefaultButton();
        btnOK.setText("Save");
        setSaveAction(this::handleUpdateButtonAction);
        add(btnOK, 1, 11, 2, 1);
    }

    public QuestionDetailPane() {
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
        categoryField.getSelectionModel().select(0);

        add(new Label("Feedback: "), 0, 10, 1, 1);
        feedbackField = new TextField();
        add(feedbackField, 1, 10, 2, 1);

        btnCancel = new Button("Cancel");
        btnCancel.setText("Cancel");
        setCancelAction(this::handleCancelButtonAction);
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

    public void setErrorOkAction(EventHandler<ActionEvent> errorOkAction) {
        btnError.setOnAction(errorOkAction);
    }

    public void handleAddButtonAction(ActionEvent event) {
        if (!statementField.getText().isEmpty() && !statementList.stream().anyMatch(x -> x.equals(statementField.getText()))) {
            statementList.add(statementField.getText());
            statementsArea.setText(StringUtils.join(statementList, "\n"));
            statementField.setText("");
        }
    }

    public void handleRemoveButtonAction(ActionEvent event) {
        String deleteString = statementField.getText();
        if (!deleteString.isEmpty() && statementList.stream().anyMatch(x -> x.equals(deleteString))) {
            statementList.remove(deleteString);
            statementsArea.setText(StringUtils.join(statementList, "\n"));
            statementField.setText("");
        }
    }

    public void handleSaveButtonAction(ActionEvent event) {
        Question question = new Question();
        question.setQuestion(questionField.getText());
        question.setCategory(((Category) categoryField.getValue()).getTitle());

        try {
            question.setCorrectAnswer(statementList.get(0));
        } catch (IndexOutOfBoundsException e) {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            btnError = new Button("Ok");
            VBox vbox;
            vbox = new VBox(new Text("No statements were given"), btnError);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(30));

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
            setErrorOkAction(this::handleErrorButtonAction);
        }

        question.setAnswers(FXCollections.observableArrayList(statementList));
        question.setFeedback(feedbackField.getText());
        BadDb.getInstance().getQuestionList().add(question);
        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    public void handleUpdateButtonAction(ActionEvent event) {
        questionToUpdate.setQuestion(questionField.getText());
        questionToUpdate.setCategory(((Category) categoryField.getValue()).getTitle());

        try {
            questionToUpdate.setCorrectAnswer(statementList.get(0));
        } catch (IndexOutOfBoundsException e) {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            btnError = new Button("Ok");
            VBox vbox;
            vbox = new VBox(new Text("No statements were given"), btnError);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(30));

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
            setErrorOkAction(this::handleErrorButtonAction);
        }

        questionToUpdate.setAnswers(FXCollections.observableArrayList(statementList));
        questionToUpdate.setFeedback(feedbackField.getText());
        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    public void handleCancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void handleErrorButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnError.getScene().getWindow();
        stage.close();
    }
}
