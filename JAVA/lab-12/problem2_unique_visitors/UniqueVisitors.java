import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = List.of("neev", "riya", "neev", "aarav", "riya", "dev", "yug");
        Set<String> uniqueSet = new HashSet<>(logins);
        Set<String> orderedSet = new LinkedHashSet<>(logins);

        System.out.println("Total login attempts: " + logins.size());
        System.out.println("Unique active count (HashSet): " + uniqueSet.size());
        System.out.print("First 3 unique visitors (LinkedHashSet): ");
        int taken = 0;
        for (String u : orderedSet) {
            if (taken == 3) break;
            System.out.print(u + (taken < 2 ? ", " : "\n"));
            taken++;
        }
    }
}

