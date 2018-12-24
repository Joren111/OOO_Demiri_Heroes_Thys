package view.panels;

import db.BadDb;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Question;

import java.util.ArrayList;

public class QuestionOverviewPane extends GridPane {
    private TableView table;
    private Button btnNew,btnEdit,btnError;

    public QuestionOverviewPane() throws Exception {
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Questions:"), 0, 0, 1, 1);

        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        table.setItems(BadDb.getInstance().getQuestionList());
        TableColumn questCol = new TableColumn<>("Question");
        questCol.setCellValueFactory(new PropertyValueFactory<>("question"));

        table.getColumns().add(questCol);
        TableColumn catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(new PropertyValueFactory("category"));
        table.getColumns().add(catCol);
        this.add(table, 0, 1, 2, 6);

        btnNew = new Button("New");
        setNewAction(this::handleButtonAction);
        this.add(btnNew, 0, 11, 1, 1);

        btnEdit = new Button("Edit");
        setEditAction(this::handleEditAction);
        this.add(btnEdit, 2, 11, 1, 1);
    }

    private void handleButtonAction(ActionEvent event){
        GridPane secondaryLayout = null;
        try{
            secondaryLayout = new QuestionDetailPane();
        } catch (Exception e){
            e.printStackTrace();
        }
        Scene newQuestionScene = new Scene(secondaryLayout,500,300);
        Stage newQuestionWindow = new Stage();
        newQuestionWindow.setScene(newQuestionScene);
        newQuestionWindow.show();
    }

    public void setNewAction(EventHandler<ActionEvent> newAction) {
        btnNew.setOnAction(newAction);
    }

    public void setEditAction(EventHandler<ActionEvent> editAction) {
        btnEdit.setOnAction(editAction);
    }

    public void handleEditAction(ActionEvent event){
        GridPane editLayout = null;
        Object o = table.getSelectionModel().getSelectedItem();
        if(o != null){
            if(o instanceof Question){
                Question question = (Question) o;
                editLayout = new QuestionDetailPane(question.getQuestion(),question.getFeedback(), question.getCategory(), question.getAnswers());
                Scene newEditScene = new Scene(editLayout,500,300);
                Stage newEditWindow = new Stage();
                newEditWindow.setScene(newEditScene);
                newEditWindow.show();
            }
        }else{
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            btnError = new Button("Ok");
            VBox vbox;
            vbox = new VBox(new Text("Error! No question selected!"), btnError);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(30));

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
            setErrorOkAction(this::handleErrorButtonAction);
        }
    }

    public void setErrorOkAction(EventHandler<ActionEvent> errorOkAction) { btnError.setOnAction(errorOkAction);}

    public void handleErrorButtonAction(ActionEvent event){
        Stage stage = (Stage) btnError.getScene().getWindow();
        stage.close();
    }

}
