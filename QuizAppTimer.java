package application;
import java.util.ArrayList;

class QuizQuestion {
    private String question;
    private ArrayList<String> options;
    private int correctAnswerIndex;

    public QuizQuestion(String question, ArrayList<String> options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}


package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApp {
	    private static ArrayList<QuizQuestion> quizQuestions = new ArrayList<>();
	    private static int userScore = 0;
	    private static int currentQuestionIndex = 0;
	    private static Timer timer;

	    public static void main(String[] args) {
	        initializeQuiz();
	        Scanner scanner = new Scanner(System.in);
	        timer = new Timer();

	        for (QuizQuestion question : quizQuestions) {
	            displayQuestion(question);
	            timer.schedule(new TimerTask() { public void run() { evaluateAnswer(-1); }}, 10000);
	            evaluateAnswer(getUserAnswer(scanner));
	        }
	        System.out.println("Quiz completed! Your final score is: " + userScore);
	        scanner.close();
	    }

	    private static void initializeQuiz() {
	        quizQuestions.add(new QuizQuestion("Capital of France?", new ArrayList<>(List.of("Berlin", "Madrid", "Paris", "Rome")), 2));
	        quizQuestions.add(new QuizQuestion("Red Planet?", new ArrayList<>(List.of("Mars", "Jupiter", "Venus", "Saturn")), 0));
	        // Add more questions as needed
	    }

	    private static void displayQuestion(QuizQuestion question) {
	        System.out.println("\nQuestion: " + question.getQuestion());
	        for (int i = 0; i < question.getOptions().size(); i++) System.out.println((i + 1) + ". " + question.getOptions().get(i));
	        System.out.print("Your answer (1-" + question.getOptions().size() + "): ");
	    }

	    private static int getUserAnswer(Scanner scanner) {
	        int userAnswer = -1;
	        while (userAnswer < 1 || userAnswer > 4) try { userAnswer = scanner.nextInt(); if (userAnswer < 1 || userAnswer > 4) System.out.print("Invalid. Enter 1-4: "); } catch (java.util.InputMismatchException e) { System.out.print("Invalid. Enter 1-4: "); scanner.next(); }
	        return userAnswer - 1;
	    }

	    private static void evaluateAnswer(int userAnswer) {
	        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

	        if (userAnswer == currentQuestion.getCorrectAnswerIndex()) {
	            System.out.println("Correct!");
	            userScore++;
	        } else if (userAnswer == -1) {
	            System.out.println("Time's up! Correct answer was " + (currentQuestion.getCorrectAnswerIndex() + 1));
	        } else {
	            System.out.println("Incorrect! Correct answer was " + (currentQuestion.getCorrectAnswerIndex() + 1));
	        }

	        // Check if there are more questions before incrementing currentQuestionIndex
	        if (currentQuestionIndex < quizQuestions.size() - 1) {
	            currentQuestionIndex++;
	        } else {
	            // All questions have been answered, stop the quiz or perform any additional actions
	            System.out.println("Quiz completed! Your final score is: " + userScore);
	            timer.cancel();  // Cancel the timer to prevent any scheduled tasks from running
	            System.exit(0);   // Terminate the program
	        }
	    }

	}

