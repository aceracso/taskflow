package dev.taskflow.task;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final List<Task> tasks = List.of(
            new Task(1, "Learn dependency injection", true),
            new Task(2, "Build the TaskFlow API", false));

    public List<Task> findAll() {
        return tasks;
    }
}