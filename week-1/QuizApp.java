import java.util.ArrayList;
import java.util.Scanner;

// Class to represent a single quiz question
class QuizQuestion {
    String questionText;   // Question to be asked
    String[] answerOptions; // Multiple-choice options for the question
    int correctAnswerIndex; // Index of the correct answer

    // Constructor for initializing a quiz question
    public QuizQuestion(String questionText, String[] answerOptions, int correctAnswerIndex) {
        this.questionText = questionText;
        this.answerOptions = answerOptions;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

// Class to represent a complete quiz
class Quiz {
    String quizTopic;                  // The topic of the quiz
    ArrayList<QuizQuestion> questions; // List of questions for the quiz

    // Constructor to initialize the quiz with a topic
    public Quiz(String quizTopic) {
        this.quizTopic = quizTopic;
        this.questions = new ArrayList<>();
    }

    // Method to add a question to the quiz
    public void addQuestion(QuizQuestion question) {
        questions.add(question);
    }

    // Method to take the quiz
    public void takeQuiz() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;  // To track the user's score

        // Iterate through each question to Print the question
        for (QuizQuestion question : questions) {
            System.out.println(question.questionText);

            // Print the answer options (A, B, C, D)
            for (int i = 0; i < question.answerOptions.length; i++) {
                System.out.println((char) ('A' + i) + ". " + question.answerOptions[i]);
            }

            // Get user's answer
            System.out.print("Your answer: ");
            char userAnswer = scanner.next().toUpperCase().charAt(0); // Convert to uppercase for consistency

            // Check if the user's answer is correct
            if (userAnswer - 'A' == question.correctAnswerIndex) {
                score++;
                System.out.println("Correct!\n");
            } else {
                System.out.println("Wrong! The correct answer is: " + (char) ('A' + question.correctAnswerIndex) + "\n");
            }
        }

        // Display the user's final score
        System.out.println("Your total score: " + score + "/" + questions.size());

        scanner.close();
    }
}

// Main class to handle user interaction with the quiz application
public class QuizApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Quiz> quizList = new ArrayList<>();  // List to store all quizzes

        while (true) {
            System.out.println("\n=== Quiz Application ===");
            System.out.println("1. Create a New Quiz");
            System.out.println("2. Take a Quiz");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            // Check for invalid input (non-integer)
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input, please enter a number.");
                scanner.next(); // Clear the invalid input
                continue;
            }

            int userChoice = scanner.nextInt();
            scanner.nextLine();
            switch (userChoice) {
                case 1:
                    // Create a new quiz
                    System.out.print("Enter the quiz topic: ");
                    String topic = scanner.nextLine();
                    Quiz newQuiz = new Quiz(topic);

                    while (true) {
                        // Add questions to the quiz
                        System.out.print("Enter a question (or type 'done' to finish): ");
                        String questionText = scanner.nextLine();
                        if (questionText.equalsIgnoreCase("done")) {
                            break;
                        }

                        String[] options = new String[4];
                        for (int i = 0; i < 4; i++) {
                            System.out.print("Enter option " + (char) ('A' + i) + ": ");
                            options[i] = scanner.nextLine();
                        }

                        System.out.print("Enter the correct option (A/B/C/D): ");
                        int correctOptionIndex = scanner.next().toUpperCase().charAt(0) - 'A';
                        scanner.nextLine();

                        // Add the question to the quiz
                        newQuiz.addQuestion(new QuizQuestion(questionText, options, correctOptionIndex));
                    }

                    // Add the completed quiz to the list of quizzes
                    quizList.add(newQuiz);
                    System.out.println("Quiz created successfully!");
                    break;

                case 2:
                    // Take an existing quiz
                    if (quizList.isEmpty()) {
                        System.out.println("No quizzes available.");
                    } else {
                        System.out.println("\nAvailable Quizzes:");
                        for (int i = 0; i < quizList.size(); i++) {
                            System.out.println((i + 1) + ". " + quizList.get(i).quizTopic);
                        }

                        System.out.print("Select a quiz to take: ");
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.next();
                            continue;
                        }

                        int quizIndex = scanner.nextInt() - 1;
                        scanner.nextLine();

                        if (quizIndex >= 0 && quizIndex < quizList.size()) {
                            quizList.get(quizIndex).takeQuiz();
                        } else {
                            System.out.println("Invalid quiz number.");
                        }
                    }
                    break;

                case 3:
                    // Exit the program
                    System.out.println("Exiting... Goodbye!");
                    return;

                default:
                    // Handle invalid menu choice
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }
}
