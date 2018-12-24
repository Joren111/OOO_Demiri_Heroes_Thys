package model;

import db.BadDb;

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
		List<String> list = new ArrayList<String>();
		if(!allCorrect(selectedAnswers)){
			int score, total, all=0, allscore=0;
			String correct, answer, resultaat = "";
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

		}else{
			list.add("Schitterend! Alles perfect!");
		}
		return list;
	}

	public List<String> feedback(List<String> selectedAnswers){
		List<String> list = new ArrayList<String>();
		if(!allCorrect(selectedAnswers)){
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
		}else{
			list.add("Schitterend! Alles perfect!");
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

	private boolean allCorrect(List<String> selectedAnswers){
		int score = 0, total = 0, all=0, allscore=0;
		String correct, answer;
		for(int i=0; i < this.getCategoryList().size(); i++){
			total = 0;
			score = 0;
			for(int j=0; j < this.questions.size(); j++){
				if(getCategoryList().get(i).equals(questions.get(j).getCategory())){
					total++;
					answer = selectedAnswers.get(i);
					correct = this.questions.get(i).getCorrectAnswer();
					if(answer.equals(correct)){
						score++;
					}
				}
			}
			all += total;
			allscore += score;
		}
		if(all==allscore){
			return true;
		}else{
			return false;
		}
	}
}
