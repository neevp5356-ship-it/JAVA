import java.util.Scanner;

public class TaskApp {
    public static void main(String[] args) {
        TaskService service = new TaskService();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== 1. Layered Application Console ===");
        System.out.print("Adding sample task: 'Complete Lab 14'... ");
        service.addTask("Complete Lab 14");
        System.out.println("Success! Total tasks: " + service.count());
        System.out.println("Testing invalid task (blank description):");
        try {
            service.addTask(" ");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Expected Error: " + e.getMessage());
        }
        System.out.println("\nCurrent Registered Tasks:");
        for (Task t : service.getAll()) {
            System.out.println(" - " + t.getTitle());
        }
        sc.close();
    }
}

