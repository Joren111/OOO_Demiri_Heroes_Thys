package domain.model;

import domain.db.BadDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Correct {
	private List<Question> questions;
	private List<Category> categories;
	public Correct(){
		this.questions = BadDb.getInstance().getQuestionList();
		this.categories = BadDb.getInstance().getCategoryList();
	}
	public String correct(List<String> selectedAnswers){
		int score = 0, total=0;
		String correct, answer, resultaat = "";
		for(int i=0; i < this.questions.size(); i++){
			correct = this.questions.get(i).getCorrectAnswer();
			answer = selectedAnswers.get(i);
			total++;
			if(answer.equals(correct)){
				score++;
			}
		}
		resultaat = score + "/" + total;
		return "score=" + resultaat;
	}

	public List<String> correctPerCategory(List<String> selectedAnswers){
		int score, total, all=0, allscore=0;
		List<String> list = new ArrayList<String>();
		String correct, answer, resultaat = "";
		System.out.println(this.getCategoryList().size());
		for(int i=0; i < this.getCategoryList().size(); i++){
			total = 0;
			score = 0;
			for(int j=0; j < this.questions.size(); j++){
				if(getCategoryList().get(i).equals(questions.get(j).getCategory())){
					total++;
					correct = this.questions.get(i).getCorrectAnswer();
					answer = selectedAnswers.get(i);
					if(answer.equals(correct)){
						score++;
					}
				}
			}
			resultaat = getCategoryList().get(i) + score + "/" + total;
			list.add(resultaat);
			all += total;
			allscore += score;
		}
		resultaat = "score=" + allscore + "/" + all;
		list.add(resultaat);
		return list;
	}

	public List<String> feedback(List<String> selectedAnswers){
		List<String> list = new ArrayList<>();
		String correct, answer, feedback = "";
		for(int i=0; i < this.getCategoryList().size(); i++){

			for(int j=0; j < this.questions.size(); j++){
				if(getCategoryList().get(i).equals(questions.get(j).getCategory())){
					correct = this.questions.get(i).getCorrectAnswer();
					answer = selectedAnswers.get(i);
					if(!answer.equals(correct)){
						feedback = questions.get(j).getFeedback();
						list.add(feedback);
					}
				}
			}
		}
		return list;
	}

	private List<String> getCategoryList(){
		Set<String> categoriesSet = new TreeSet<String>();
		for(int i = 0; i< this.categories.size(); i++){
			categoriesSet.add(this.categories.get(i).getTitle());
		}
		List<String> list = new ArrayList<String>(categoriesSet);
		return list;
	}
}
