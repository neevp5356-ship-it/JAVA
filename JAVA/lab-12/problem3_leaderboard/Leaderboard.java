import java.util.*;

public class Leaderboard {
    public static void main(String[] args) {
        Map<String, Integer> scoreBoard = new HashMap<>();
        scoreBoard.put("Neev", 950);
        scoreBoard.put("Aarav", 880);
        scoreBoard.put("Riya", 950);
        scoreBoard.put("Dev", 720);
        scoreBoard.put("Yug", 910);

        int topLimit = 3;
        List<Map.Entry<String, Integer>> list = new ArrayList<>(scoreBoard.entrySet());
        list.sort(
            Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey)
        );

        System.out.println("=== Top " + topLimit + " Leaderboard ===");
        for (int i = 0; i < Math.min(topLimit, list.size()); i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            System.out.println((i + 1) + ". " + entry.getKey() + " (" + entry.getValue() + " pts)");
        }
    }
}

