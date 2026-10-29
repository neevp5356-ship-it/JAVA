package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class TransactionLog {
    public static synchronized void append(Path file, String line) {
        try {
            if (!Files.exists(file.getParent())) {
                Files.createDirectories(file.getParent());
            }
            String logEntry = "[" + LocalDateTime.now() + "] " + line + "\n";
            Files.write(file, logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write to transaction log: " + e.getMessage());
        }
    }
}
