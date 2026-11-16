package dev.taskflow.task;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class TaskService {


    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    //private final List<Task> tasks = new ArrayList<>();

    public TaskService(TaskSeedProperties seed) {
        if (seed.enabled()) {
            for (String title : seed.titles()) {
                create(title);
            }
        }
    }

    public List<Task> findAll() {
        return tasks.values().stream()
                .sorted(Comparator.comparingLong(Task::id))
                .toList();
    }
    
    public Task get(long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    public Task create(String title) {
        long id = nextId.getAndIncrement();
        Task task = new Task(id, title, false);
        tasks.put(id, task);
        return task;
    }

    public Task update(long id, String title, boolean done) {
        Task updated = tasks.computeIfPresent(id, (key, old) -> new Task(key, title, done));
        if (updated == null) {
            throw new TaskNotFoundException(id);
        }
        return updated;
    }

    public void delete(long id) {
        if (tasks.remove(id) == null) {
            throw new TaskNotFoundException(id);
        }
    }
}