package model.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtDatabaseStrategy implements DatabaseStrategy {
    private ObservableList<Category> categoryList;
    private ObservableList<Question> questionList;

    @Override
    public void load() {
        loadCategoryList();
        loadQuestionList();
    }

    @Override
    public void save() {

    }

    @Override
    public ObservableList<Category> getCategoryList() {
        return categoryList;
    }

    @Override
    public ObservableList<Question> getQuestionList() {
        return questionList;
    }

    private void loadCategoryList() {
        List<Category> list = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("C:/Users/Admin/OOO_Demiri_Heroes_Thys/testdatabase/groep.txt"));
            String s;
            while ((s = br.readLine()) != null) {
                Category category = new Category();
                String[] data = s.split(",");
                category.setTitle(data[0]);
                category.setDescription(data[1]);

                // size of 3 means that category has a subcategory
                if (data.length == 3)
                    category.setSubCategory(list.stream().filter(x -> x.getTitle().equals(data[2])).findFirst().get());

                list.add(category);
            }

            categoryList = FXCollections.observableArrayList(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuestionList() {
        List<Question> list = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("C:/Users/Admin/OOO_Demiri_Heroes_Thys/testdatabase/vraag.txt"));
            String s;
            while ((s = br.readLine()) != null) {
                Question question = new Question();
                String[] data = s.split(",");
                question.setQuestion(data[0]);
                question.setCategory(data[1]);
                list.add(question);
            }

            questionList = FXCollections.observableArrayList(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
