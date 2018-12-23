package application;

import controller.TestController;
import model.Correct;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.panels.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		try {
			QuestionOverviewPane questionOverviewPane = new QuestionOverviewPane();
			QuestionDetailPane questionDetailPane = new QuestionDetailPane();

			CategoryOverviewPane categoryOverviewPanel = new CategoryOverviewPane();
			CategoryDetailPane categoryDetailPanel = new CategoryDetailPane();

			MessagePane messagePane = new MessagePane();

			EvaluationChangePane evaluationChangePane = new EvaluationChangePane();

			Correct correct = new Correct();
			TestController testController = new TestController(messagePane, correct);
			messagePane.addTestController(testController);

			Group root = new Group();
			Scene scene = new Scene(root, 750, 400);

			BorderPane borderPane = new AssesMainPane(messagePane, categoryOverviewPanel, questionOverviewPane, evaluationChangePane);
			borderPane.prefHeightProperty().bind(scene.heightProperty());
			borderPane.prefWidthProperty().bind(scene.widthProperty());

			root.getChildren().add(borderPane);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
