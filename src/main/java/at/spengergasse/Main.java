package at.spengergasse;

import at.spengergasse.entities.Answer;
import at.spengergasse.entities.UserResponse;
import at.spengergasse.entities.Question;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("demo")
                .createEntityManager();

        Scanner scanner = new Scanner(System.in);

        TypedQuery<Question> queryQue = em.createQuery("SELECT q from Question q", Question.class);
        List<Question> questions = queryQue.getResultList();

        char[] answerChar = new char[]{'a', 'b', 'c', 'd'};

        System.out.println("___________Quiz___________");

        int correctAnswersCount = 0;  // Richtige Antworten zählen
        int totalQuestions = questions.size(); // Gesamtzahl der Fragen

        for (Question q : questions) {
            String rightAnswer = "";
            List<Answer> answers = q.getAnswers();
            System.out.println(q.getText());
            System.out.println("Selection: ");

            for (int i = 0; i < answers.size(); i++) {
                System.out.println(answerChar[i] + ") " + answers.get(i).getText());
                if (answers.get(i).isCorrect()) {
                    rightAnswer = String.valueOf(answerChar[i]);
                }
            }

            boolean correctInput = false;
            String input = "";
            while (!correctInput) {
                System.out.print("Your Answer: ");
                input = scanner.next();
                if (!input.matches("[a-d]")) {
                    System.out.println("Bitte nur a - d eingeben!");
                } else {
                    correctInput = true;
                }
            }

            if (input.equals(rightAnswer)) {
                correctAnswersCount++;  // Erhöht die Anzahl der richtigen Antworten
            } else {
                System.out.println("Falsche Antwort! Die richtige Antwort war: " + rightAnswer);
            }

            System.out.println("\n--next Question--");
        }

        // Ergebnisberechnung
        double percentage = ((double) correctAnswersCount / totalQuestions) * 100;

        System.out.println("\n### Quiz beendet ###");
        System.out.println("Richtige Antworten: " + correctAnswersCount + " von " + totalQuestions);
        System.out.printf("Erfolgsquote: %.2f%%\n", percentage);

        em.close();
    }
}
