package app.service;

import app.model.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Cannot be blank");
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

