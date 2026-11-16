package dev.taskflow.task;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> list() {
        return taskService.findAll().stream().map(TaskResponse::from).toList();
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable long id) {
        return TaskResponse.from(taskService.get(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request,
                                               UriComponentsBuilder uriBuilder) {
        Task task = taskService.create(request.title());
        URI location = uriBuilder.path("/api/tasks/{id}").buildAndExpand(task.id()).toUri();
        return ResponseEntity.created(location).body(TaskResponse.from(task));
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable long id,
                               @Valid @RequestBody UpdateTaskRequest request) {
        return TaskResponse.from(taskService.update(id, request.title(), request.done()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}