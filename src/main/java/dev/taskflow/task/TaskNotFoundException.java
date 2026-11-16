package dev.taskflow.task;

public class TaskNotFoundException extends RuntimeException {

    private final long id;

    public TaskNotFoundException(long id) {
        super("Task " + id + " not found");
        this.id = id;
    }

    public long getId() {
        return id;
    }
}