import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be blank.");
        }
        tasks.add(new Task(title.trim()));
    }

    public int count() {
        return tasks.size();
    }

    public List<Task> getAll() {
        return new ArrayList<>(tasks);
    }
}

