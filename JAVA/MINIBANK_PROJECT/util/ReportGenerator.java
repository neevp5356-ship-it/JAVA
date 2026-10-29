package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {
    public static String generateDailyReport(Path logsDir, Path reportFile) {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int filesProcessed = 0;

        try {
            if (Files.exists(logsDir)) {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir, "*.{log,txt}")) {
                    for (Path entry : stream) {
                        if (Files.size(entry) == 0) continue;
                        filesProcessed++;
                        try (BufferedReader reader = Files.newBufferedReader(entry)) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                line = line.toUpperCase();
                                if (line.contains("DEPOSIT")) {
                                    String[] parts = line.split("\\s+");
                                    try {
                                        long amt = Long.parseLong(parts[parts.length - 1]);
                                        totalDeposits += amt;
                                    } catch (NumberFormatException ignored) {}
                                } else if (line.contains("WITHDRAW") || line.contains("TRANSFER")) {
                                    String[] parts = line.split("\\s+");
                                    try {
                                        long amt = Long.parseLong(parts[parts.length - 1]);
                                        totalWithdrawals += amt;
                                    } catch (NumberFormatException ignored) {}
                                }
                            }
                        }
                    }
                }
            }

            StringBuilder report = new StringBuilder();
            report.append("=========================================\n");
            report.append("          MINIBANK END-OF-DAY REPORT     \n");
            report.append("=========================================\n");
            report.append("Logs Processed    : ").append(filesProcessed).append("\n");
            report.append("Total Deposits    : ₹").append(totalDeposits).append("\n");
            report.append("Total Withdrawals : ₹").append(totalWithdrawals).append("\n");
            report.append("Net Flow          : ₹").append(totalDeposits - totalWithdrawals).append("\n");
            report.append("=========================================");

            if (reportFile != null) {
                if (!Files.exists(reportFile.getParent())) {
                    Files.createDirectories(reportFile.getParent());
                }
                Files.write(reportFile, report.toString().getBytes());
            }

            return report.toString();
        } catch (IOException e) {
            return "Error generating report: " + e.getMessage();
        }
    }
}
