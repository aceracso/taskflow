package dev.taskflow.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

class TaskControllerTest {

    private final TaskController controller =
            new TaskController(new TaskService(new TaskSeedProperties(false, List.of())));

    @Test
    void listReturnsEveryTask() {
        var seed = new TaskSeedProperties(true, List.of("Write tests", "Ship it"));
        TaskController controller = new TaskController(new TaskService(seed));

        assertEquals(2, controller.list().size());
    }

    @Test
    void listIsEmptyWhenSeedingIsDisabled() {
        assertEquals(0, controller.list().size());
    }

    @Test
    void createReturns201WithLocation() {
        var response = controller.create(new CreateTaskRequest("Write tests"),
                UriComponentsBuilder.fromUriString("http://localhost"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("http://localhost/api/tasks/1", response.getHeaders().getLocation().toString());
        assertEquals("Write tests", response.getBody().title());
    }

    @Test
    void getUnknownIdThrowsTaskNotFound() {
        var ex = assertThrows(TaskNotFoundException.class, () -> controller.get(99));
        assertEquals(99, ex.getId());
    }

    @Test
    void deleteTwiceReturns204ThenThrows() {
        controller.create(new CreateTaskRequest("Temp"), UriComponentsBuilder.newInstance());

        assertEquals(HttpStatus.NO_CONTENT, controller.delete(1).getStatusCode());
        assertThrows(TaskNotFoundException.class, () -> controller.delete(1));
    }
}