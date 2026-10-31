import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {
    @Test
    void testAddingTaskIncreasesCount() {
        TaskService service = new TaskService();
        assertEquals(0, service.count(), "Initial count must be 0");
        service.addTask("Complete Practical 14");
        assertEquals(1, service.count(), "Count must be 1 after addition");
    }

    @Test
    void testAddingBlankTaskThrowsException() {
        TaskService service = new TaskService();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.addTask(" "));
        assertTrue(ex.getMessage().contains("cannot be blank"));
    }
}

