package at.spengergasse;

import at.spengergasse.entities.Answer;
import at.spengergasse.entities.UserResponse;
import at.spengergasse.entities.Question;
import at.spengergasse.entities.UserResponse;

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

        char[] answerChar = new char[4];
        answerChar = new char[]{'a', 'b', 'c', 'd'};

        System.out.println("___________Quiz___________");

        UserResponse user = new UserResponse();
        String input = user.getAnswer();
        String selection = "Selection: ";
        String correct = "Correct: ";



        for (Question q : questions) {

            String rightAnswer = " ";
            List<Answer> answers = q.getAnswers();
            System.out.println(q.getText());
            System.out.println(selection);
            for (int i = 0; i < q.getAnswers().size(); i++) {
                System.out.println(answerChar[i] + ") " + answers.get(i).getText());
                if (answers.get(i).isCorrect()) {
                    rightAnswer = String.valueOf(answerChar[i]);
                    correct += rightAnswer + ") " + answers.get(i).getText();
                }

            }

            boolean correctInput = false;
            while (!correctInput) {
                System.out.print("Your Answer: ");
                input = (scanner.next());
                if (!input.matches("[a-d]")) {
                    System.out.println("Bitte nur a - d eingeben!");
                }
                else
                {
                    correctInput = true;
                }
            }
            if (!input.equals(rightAnswer)) {
                System.out.println(correct);
                System.out.println("\n--next Question--");
            }
            else
            {
                System.out.println("\n--next Question--");
            }
            correct = "Correct: ";

        }
        em.close();
    }
}