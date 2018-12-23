package domain.db;

import domain.model.Category;
import domain.model.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public ObservableList<Category> getCategoryList() {
        if (categoryList == null) {
            List<Category> list = new ArrayList<>();
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader("testdatabase\\groep.txt"));
                String s;
                while ((s = br.readLine()) != null) {
                    Category category = new Category();
                    String data[] = s.split(",");
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

        return this.categoryList;
    }

    public ObservableList<Question> getQuestionList() {
        if (questionList == null) {
            List<Question> list = new ArrayList<>();
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader("testdatabase\\vraag.txt"));
                String s;
                while ((s = br.readLine()) != null) {
                    Question question = new Question();
                    List<String> answers = new ArrayList<>();
                    ObservableList<String> answersList;
                    String data[] = s.split(",");
                    question.setFeedback(data[2]);
                    question.setQuestion(data[0]);
                    question.setCategory(data[1]);
                    question.setCorrectAnswer(data[3]);
                    for(int i=3; i < data.length; i++){
                    	answers.add(data[i]);
                    }
                    answersList = FXCollections.observableArrayList(answers);
                    question.setAnswers(answersList);
                    list.add(question);
                }

                questionList = FXCollections.observableArrayList(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return questionList;
    }
}
