package view.panels;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Category;
import model.db.BadDb;

public class CategoryDetailPane extends GridPane {
    private Button btnOK, btnCancel;
    private TextField titleField, descriptionField;
    private ComboBox categoryField;
    private Category categoryToUpdate;

    public CategoryDetailPane(Category categoryToUpdate) {
        this.categoryToUpdate = categoryToUpdate;

        this.setPrefHeight(150);
        this.setPrefWidth(300);

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Title:"), 0, 0, 1, 1);
        titleField = new TextField();
        titleField.setText(categoryToUpdate.getTitle());
        this.add(titleField, 1, 0, 1, 1);

        this.add(new Label("Description:"), 0, 1, 1, 1);
        descriptionField = new TextField();
        descriptionField.setText(categoryToUpdate.getDescription());
        this.add(descriptionField, 1, 1, 1, 1);

        this.add(new Label("Main Category:"), 0, 2, 1, 1);
        categoryField = new ComboBox<>();
        categoryField.setItems(BadDb.getInstance().getCategoryList());
        this.add(categoryField, 1, 2, 1, 1);

        btnCancel = new Button("Cancel");
        setCancelAction(this::handleCancelButtonAction);
        this.add(btnCancel, 0, 3, 1, 1);

        btnOK = new Button("Update");
        btnOK.isDefaultButton();
        setSaveAction(this::handleUpdateButtonAction);
        this.add(btnOK, 1, 3, 1, 1);
    }

    public CategoryDetailPane() throws Exception {
        this.setPrefHeight(150);
        this.setPrefWidth(300);

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Title:"), 0, 0, 1, 1);
        titleField = new TextField();
        this.add(titleField, 1, 0, 1, 1);

        this.add(new Label("Description:"), 0, 1, 1, 1);
        descriptionField = new TextField();
        this.add(descriptionField, 1, 1, 1, 1);

        this.add(new Label("Main Category:"), 0, 2, 1, 1);
        categoryField = new ComboBox<>();
        categoryField.setItems(BadDb.getInstance().getCategoryList());
        this.add(categoryField, 1, 2, 1, 1);

        btnCancel = new Button("Cancel");
        setCancelAction(this::handleCancelButtonAction);
        this.add(btnCancel, 0, 3, 1, 1);

        btnOK = new Button("Save");
        btnOK.isDefaultButton();
        setSaveAction(this::handleSaveButtonAction);
        this.add(btnOK, 1, 3, 1, 1);
    }

    public void setSaveAction(EventHandler<ActionEvent> saveAction) {
        btnOK.setOnAction(saveAction);
    }

    public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
        btnCancel.setOnAction(cancelAction);
    }

    private void handleSaveButtonAction(ActionEvent event) {
        Category category = new Category();
        category.setTitle(titleField.getText());
        category.setDescription(descriptionField.getText());

        Category subCategorySelected = (Category) categoryField.getValue();
        if (subCategorySelected != null) {
            category.setSubCategory(BadDb.getInstance().getCategoryList().
                    stream().
                    filter(x -> x.getTitle().equals(subCategorySelected.getTitle())).
                    findFirst()
                    .get());
        }

        BadDb.getInstance().getCategoryList().add(category);
        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private void handleUpdateButtonAction(ActionEvent event) {
        if (categoryToUpdate != null) {
            categoryToUpdate.setTitle(titleField.getText());
            categoryToUpdate.setDescription(descriptionField.getText());

            Category subCategorySelected = (Category) categoryField.getValue();
            if (subCategorySelected != null) {
                categoryToUpdate.setSubCategory(BadDb.getInstance().getCategoryList().
                        stream().
                        filter(x -> x.getTitle().equals(subCategorySelected.getTitle())).
                        findFirst()
                        .get());
            } else {
                categoryToUpdate.setSubCategory(null);
            }
        }

        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private void handleCancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
