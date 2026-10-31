import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {
    public static void main(String[] args) {
        int poolSize = 3;
        int totalTasks = 10;

        System.out.println("=== 1. Thread-Pool Runner ===");
        System.out.println("Creating fixed thread pool of size: " + poolSize);
        System.out.println("Submitting " + totalTasks + " tasks...\n");

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        for (int i = 1; i <= totalTasks; i++) {
            final int taskId = i;
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("[START] Task " + taskId + " started on " + threadName);
                try {
                    // Simulate work
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Task " + taskId + " was interrupted.");
                }
                System.out.println("[DONE]  Task " + taskId + " completed on " + threadName);
            });
        }

        System.out.println("All tasks submitted. Initiating shutdown...\n");
        executor.shutdown();

        try {
            // Await completion of all submitted tasks
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Tasks took longer than expected; forcing shutdown...");
                executor.shutdownNow();
            } else {
                System.out.println("\nAll tasks completed successfully within timeout.");
            }
        } catch (InterruptedException e) {
            System.err.println("Thread pool awaitTermination interrupted.");
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("ExecutorService terminated. Notice how pool threads (e.g., thread-1, thread-2, thread-3) were reused across all 10 tasks.");
    }
}
