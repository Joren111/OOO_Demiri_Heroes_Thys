package view.panels;

import domain.Category;
import domain.db.BadDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class CategoryOverviewPane extends GridPane {
    private TableView<Category> table;
    private Button btnNew;

    public CategoryOverviewPane() throws Exception {
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Category:"), 0, 0, 1, 1);

        table = new TableView<Category>();
        table.setPrefWidth(REMAINING);
        table.setItems(BadDb.getInstance().getCategoryList());
        TableColumn nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("title"));
        table.getColumns().add(nameCol);
        TableColumn descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        table.getColumns().add(descriptionCol);
        this.add(table, 0, 1, 2, 6);

        btnNew = new Button("New");
        setNewAction(this::handleButtonAction);
        this.add(btnNew, 0, 11, 1, 1);
    }

    private void handleButtonAction(ActionEvent event) {
        GridPane secondaryLayout = null;
        try {
            secondaryLayout = new CategoryDetailPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scene newCategoryScene = new Scene(secondaryLayout, 310, 155);
        Stage newCategoryWindow = new Stage();
        newCategoryWindow.setScene(newCategoryScene);
        newCategoryWindow.show();
    }

    public void setNewAction(EventHandler<ActionEvent> newAction) {
        btnNew.setOnAction(newAction);
    }

    public void setEditAction(EventHandler<MouseEvent> editAction) {
        table.setOnMouseClicked(editAction);
    }

}
