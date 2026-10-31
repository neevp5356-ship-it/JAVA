package app.ui;

import app.model.Task;
import app.service.TaskService;
import java.util.Scanner;

public class TaskApp {
    public static void main(String[] args) {
        TaskService service = new TaskService();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== 2. Modular JAR Application (app.ui.TaskApp) ===");
        service.addTask("Charusat Practical Evaluation");
        service.addTask("JUnit 5 Verification");
        System.out.println("Loaded tasks successfully from package app.service:");
        for (Task t : service.getAll()) {
            System.out.println(" • " + t.getTitle());
        }
        System.out.println("Total package managed records: " + service.count());
    }
}

