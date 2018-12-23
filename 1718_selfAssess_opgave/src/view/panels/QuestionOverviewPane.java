package view.panels;

import domain.db.BadDb;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.plaf.ActionMapUIResource;

public class QuestionOverviewPane extends GridPane {
    private TableView table;
    private Button btnNew;

    public QuestionOverviewPane() throws Exception {
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Questions:"), 0, 0, 1, 1);

        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        table.setItems(BadDb.getInstance().getQuestionList());
        TableColumn nameCol = new TableColumn<>("Question");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("question"));
        table.getColumns().add(nameCol);
        TableColumn descriptionCol = new TableColumn<>("Category");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("category"));
        table.getColumns().add(descriptionCol);
        this.add(table, 0, 1, 2, 6);

        btnNew = new Button("New");
        setNewAction(this::handleButtonAction);
        this.add(btnNew, 0, 11, 1, 1);
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

    public void setEditAction(EventHandler<MouseEvent> editAction) {
        table.setOnMouseClicked(editAction);
    }

}
