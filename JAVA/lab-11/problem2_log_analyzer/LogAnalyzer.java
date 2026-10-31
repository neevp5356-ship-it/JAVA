import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzer {
    public static void main(String[] args) throws IOException {
        Path log1 = Path.of("app1.log");
        Path log2 = Path.of("app2.log");

        // Write test data
        Files.writeString(log1, "INFO Server booted\nERROR Database connection refused\nINFO Request OK\n");
        Files.writeString(log2, "ERROR Disk full\nWARN Memory threshold 85%\nERROR Socket reset\n");

        Path[] targets = { log1, log2 };
        String searchTag = "ERROR";
        int totalLines = 0;
        int matchCount = 0;

        for (Path p : targets) {
            BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);
            System.out.println("File: " + p.getFileName() + " | Size: " + attrs.size() + " bytes");
            List<String> lines = Files.readAllLines(p);
            for (String l : lines) {
                totalLines++;
                if (l.contains(searchTag)) {
                    matchCount++;
                }
            }
        }

        System.out.println("Total lines scanned across all files: " + totalLines);
        System.out.println("Lines containing '" + searchTag + "': " + matchCount);
    }
}

