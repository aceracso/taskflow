package dev.taskflow.task;
    
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository repository;

    //private final List<Task> tasks = new ArrayList<>();

    public TaskService(TaskRepository repository, TaskSeedProperties seed) {
        this.repository = repository;
        if (seed.enabled() && repository.count() == 0) {
            for (String title : seed.titles()) {
                create(title);
            }
        }
    }

    public List<Task> findAll() {
        return repository.findAll(Sort.by("id"));
    }
    
    public Task get(long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task create(String title) {
        return repository.save(new Task(title));
    }

    public Task update(long id, String title, boolean done) {
        Task updated = get(id);
        updated.rename(title);
        updated.setDone(done);
        return repository.save(updated);
    }

    public void delete(long id) {
        repository.delete(get(id));
    }
}