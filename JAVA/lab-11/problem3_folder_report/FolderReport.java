import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public class FolderReport {
    public static void main(String[] args) throws IOException {
        Path rootDir = Path.of(".");
        Path reportOut = Path.of("folder_report.txt");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-25s | %-10s | %s%n", "File Name", "Size (B)", "Last Modified"));
        sb.append("-".repeat(65)).append("\n");

        try (Stream<Path> stream = Files.walk(rootDir)) {
            stream.filter(Files::isRegularFile).forEach(p -> {
                try {
                    BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
                    sb.append(String.format("%-25s | %-10d | %s%n",
                            p.getFileName().toString(),
                            attr.size(),
                            attr.lastModifiedTime().toString()));
                } catch (IOException e) {
                    System.err.println("Error reading " + p);
                }
            });
        }

        Files.writeString(reportOut, sb.toString());
        System.out.println("Report successfully written to " + reportOut.getFileName() + ":\n");
        System.out.println(Files.readString(reportOut));
    }
}

