package view.panels;

import domain.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class QuestionOverviewPane extends GridPane {
    private TableView table;
    private Button btnNew;
    private ObservableList<Question> questions;

    public QuestionOverviewPane() throws Exception {
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Questions:"), 0, 0, 1, 1);

        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        questions = (ObservableList<Question>) dataList();
        table.setItems(questions);
        TableColumn nameCol = new TableColumn<>("Question");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("question"));
        table.getColumns().add(nameCol);
        TableColumn descriptionCol = new TableColumn<>("Category");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("category"));
        table.getColumns().add(descriptionCol);
        this.add(table, 0, 1, 2, 6);

        btnNew = new Button("New");
        this.add(btnNew, 0, 11, 1, 1);
    }

    private List<Question> dataList() throws Exception {
        List<Question> list = FXCollections.observableArrayList();
        BufferedReader br = new BufferedReader(new FileReader("testdatabase\\vraag.txt"));
        String s = "";
        while ((s = br.readLine()) != null) {
            Question question = new Question();
            String data[] = new String[2];
            data = s.split(",");
            question.setQuestion(data[0]);
            question.setCategory(data[1]);
            list.add(question);
        }
        return list;
    }

    public void setNewAction(EventHandler<ActionEvent> newAction) {
        btnNew.setOnAction(newAction);
    }

    public void setEditAction(EventHandler<MouseEvent> editAction) {
        table.setOnMouseClicked(editAction);
    }

}
