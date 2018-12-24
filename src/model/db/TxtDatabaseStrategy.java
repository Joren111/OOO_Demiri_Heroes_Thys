package model.db;

import com.sun.deploy.util.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.Question;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        saveCategoryList();
        saveQuestionList();
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
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/groep.txt"), StandardCharsets.UTF_8));
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
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/vraag.txt"), StandardCharsets.UTF_8));
            String s;
            while ((s = br.readLine()) != null) {
                Question question = new Question();
                String[] data = s.split(",");
                question.setQuestion(data[0]);
                question.setCategory(data[1]);
                question.setFeedback(data[2]);

                String[] answers = data[3].split(";");
                question.setCorrectAnswer(answers[0]);

                question.setAnswers(FXCollections.observableArrayList(answers));
                list.add(question);
            }

            questionList = FXCollections.observableArrayList(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCategoryList() {
        try {
            File outputFile = new File(this.getClass().getResource("/groep.txt").getPath());
            outputFile.delete();
            outputFile.createNewFile();

            PrintWriter writer = new PrintWriter(outputFile);
            for (Category category : getCategoryList()) {
                writer.print(category.getTitle() + "," + category.getDescription());

                if (category.getSubCategory() != null)
                    writer.print("," + category.getSubCategory().getTitle());

                writer.println();
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveQuestionList() {
        try {
            File outputFile = new File(this.getClass().getResource("/vraag.txt").getPath());
            outputFile.delete();
            outputFile.createNewFile();

            PrintWriter writer = new PrintWriter(outputFile);
            for (Question question : getQuestionList()) {
                writer.println(question.getQuestion() + "," + question.getCategory() + "," + question.getFeedback() + "," + StringUtils.join(question.getAnswers(), ";"));
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
