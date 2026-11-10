package dev.taskflow.task;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public TaskService(TaskSeedProperties seed) {
        if (seed.enabled()) {
            long id = 1;
            for (String title : seed.titles()) {
                tasks.add(new Task(id++, title, false));
            }
        }
    }

    public List<Task> findAll() {
        return tasks;
    }
}