package dev.taskflow.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class TaskControllerTest {

    @Test
    void listReturnsEveryTask() {
        var seed = new TaskSeedProperties(true, List.of("Write tests", "Ship it"));
        TaskController controller = new TaskController(new TaskService(seed));

        assertEquals(2, controller.list().size());
    }

    @Test
    void listIsEmptyWhenSeedingIsDisabled() {
        var seed = new TaskSeedProperties(false, List.of("Ignored"));
        TaskController controller = new TaskController(new TaskService(seed));

        assertEquals(0, controller.list().size());
    }
}