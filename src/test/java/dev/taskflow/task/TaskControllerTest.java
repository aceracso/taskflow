package dev.taskflow.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TaskControllerTest {

    @Test
    void listReturnsEveryTask() {
        TaskController controller = new TaskController(new TaskService());

        assertEquals(2, controller.list().size());
    }
}