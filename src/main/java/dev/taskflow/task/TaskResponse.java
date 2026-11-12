package dev.taskflow.task;

public record TaskResponse(long id, String title, boolean done) {

    static TaskResponse from(Task task) {
        return new TaskResponse(task.id(), task.title(), task.done());
    }
}