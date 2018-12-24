package model.db;

import javafx.collections.ObservableList;
import model.Category;
import model.Question;

public interface DatabaseStrategy {
    void load();

    void save();

    ObservableList<Category> getCategoryList();

    ObservableList<Question> getQuestionList();
}
