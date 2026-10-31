import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        String text = "java is fast java is secure and java is platform independent fast code";
        String[] tokens = text.toLowerCase().split("\\s+");
        Map<String, Integer> counts = new HashMap<>();
        for (String w : tokens) {
            counts.put(w, counts.getOrDefault(w, 0) + 1);
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(counts.entrySet());
        entryList.sort(
            Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey)
        );

        System.out.println("Word Frequencies (Highest Frequency First):");
        for (Map.Entry<String, Integer> e : entryList) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}

