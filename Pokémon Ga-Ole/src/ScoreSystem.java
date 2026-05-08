import java.io.*;
import java.util.*;

public class ScoreSystem {
    private static final String FILE_NAME = "top_scores.txt";

    public void saveScore(int score) {
        List<Integer> scores = loadScore();
        scores.add(score);
        scores.sort(Collections.reverseOrder());
        if (scores.size() > 5) scores = scores.subList(0, 5);
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int s : scores) writer.println(s);
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    public List<Integer> loadScore() {
        List<Integer> scores = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return scores;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                scores.add(scanner.nextInt());
            }
        } catch (IOException e) {
            System.out.println("Error reading scores: " + e.getMessage());
        }
        return scores;
    }

    public void displayTopScore() {
        System.out.println("\n===== TOP SCORES =====");
        List<Integer> scores = loadScore();
        if (scores.isEmpty()) {
            System.out.println("No scores yet!");
        } else {
            for (int i = 0; i < scores.size(); i++) {
                System.out.println((i + 1) + ". " + scores.get(i));
            }
        }
    }
}
