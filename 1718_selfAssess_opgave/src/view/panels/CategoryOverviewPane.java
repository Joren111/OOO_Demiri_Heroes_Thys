package view.panels;

import domain.Category;
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

public class CategoryOverviewPane extends GridPane {
    private TableView<Category> table;
    private Button btnNew;
    private ObservableList<Category> categories;

    public CategoryOverviewPane() throws Exception {
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Category:"), 0, 0, 1, 1);

        table = new TableView<Category>();
        table.setPrefWidth(REMAINING);
        categories = (ObservableList<Category>) dataList();
        table.setItems(categories);
        TableColumn nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("title"));
        table.getColumns().add(nameCol);
        TableColumn descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        table.getColumns().add(descriptionCol);
        this.add(table, 0, 1, 2, 6);

        btnNew = new Button("New");
        this.add(btnNew, 0, 11, 1, 1);
    }

    private List<Category> dataList() throws Exception {
        List<Category> list = FXCollections.observableArrayList();
        BufferedReader br = new BufferedReader(new FileReader("testdatabase\\groep.txt"));
        String s = "";
        while ((s = br.readLine()) != null) {
            Category category = new Category();
            String data[] = new String[2];
            data = s.split(",");
            category.setTitle(data[0]);
            category.setDescription(data[1]);
            list.add(category);
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
