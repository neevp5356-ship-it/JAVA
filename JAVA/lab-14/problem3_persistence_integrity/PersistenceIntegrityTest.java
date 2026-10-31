import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class PersistenceIntegrityTest {
    @Test
    void testSaveAndReloadPreservesStateUnchanged() throws Exception {
        PersistentTaskService service1 = new PersistentTaskService();
        service1.addRecord("Task 1: Object Serialization");
        service1.addRecord("Task 2: Runnable JAR Build");
        Path tempState = Path.of("tasks_persistent_test.dat");
        service1.save(tempState);

        PersistentTaskService service2 = new PersistentTaskService();
        service2.load(tempState);

        // Assert state is preserved accurately
        assertEquals(2, service2.size(), "Reloaded count must match original count");
        assertEquals("Task 1: Object Serialization", service2.getRecords().get(0));
        assertEquals("Task 2: Runnable JAR Build", service2.getRecords().get(1));

        // Clean up temporary disk resource
        Files.deleteIfExists(tempState);
    }
}

