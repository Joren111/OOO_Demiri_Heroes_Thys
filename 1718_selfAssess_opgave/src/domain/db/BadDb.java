package domain.db;

import domain.Category;
import domain.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BadDb {
    private static BadDb instance;
    private ObservableList<Category> categoryList;
    private ObservableList<Question> questionList;

    public static BadDb getInstance() {
        if (instance == null)
            instance = new BadDb();

        return instance;
    }

    public ObservableList<Category> getCategoryList() throws Exception {
        if (categoryList == null) {
            List<Category> list = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("testdatabase\\groep.txt"));
            String s;
            while ((s = br.readLine()) != null) {
                Category category = new Category();
                String data[] = s.split(",");
                category.setTitle(data[0]);
                category.setDescription(data[1]);
                list.add(category);
            }

            categoryList = FXCollections.observableArrayList(list);
        }

        return this.categoryList;
    }

    public ObservableList<Question> getQuestionList() throws Exception {
        if (questionList == null) {
            List<Question> list = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("testdatabase\\vraag.txt"));
            String s;
            while ((s = br.readLine()) != null) {
                Question question = new Question();
                String data[] = s.split(",");
                question.setQuestion(data[0]);
                question.setCategory(data[1]);
                list.add(question);
            }

            questionList = FXCollections.observableArrayList(list);
        }

        return questionList;
    }
}
