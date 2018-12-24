package view.panels;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import model.Category;
import db.BadDb;
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
import model.Question;

public class CategoryOverviewPane extends GridPane {
    private TableView<Category> table;
    private Button btnNew,btnEdit,btnError;

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

        btnEdit = new Button("Edit");
        setEditAction(this::handleEditAction);
        this.add(btnEdit, 2, 11, 1, 1);
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

    public void setEditAction(EventHandler<ActionEvent> editAction) {
        btnEdit.setOnAction(editAction);
    }

    public void handleEditAction(ActionEvent event){
        GridPane editLayout = null;
        Object o = table.getSelectionModel().getSelectedItem();
        if(o != null){
            if(o instanceof Category){
                Category category = (Category) o;
                editLayout = new CategoryDetailPane(category.getTitle(),category.getDescription());
                Scene newEditScene = new Scene(editLayout,310,155);
                Stage newEditWindow = new Stage();
                newEditWindow.setScene(newEditScene);
                newEditWindow.show();
            }
        }else{
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            btnError = new Button("Ok");
            VBox vbox;
            vbox = new VBox(new Text("Error! No category selected!"), btnError);
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
