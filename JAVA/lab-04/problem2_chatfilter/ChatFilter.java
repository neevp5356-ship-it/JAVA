package p2_chatfilter;

/**
 * ChatFilter processes raw log lines and filters them based on a search keyword.
 */
public class ChatFilter {

    public static class FilterResult {
        private final int matchCount;
        private final String report;

        public FilterResult(int matchCount, String report) {
            this.matchCount = matchCount;
            this.report = report;
        }

        public int getMatchCount() {
            return matchCount;
        }

        public String getReport() {
            return report;
        }
    }

    public static FilterResult filterLogs(String[] logLines, String keyword) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        String lowerKeyword = keyword.toLowerCase();

        for (String line : logLines) {
            if (line == null) continue;

            String[] parts = line.split(" ", 3);
            if (parts.length < 3) {
                continue; // Skip malformed line
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            if (message.toLowerCase().contains(lowerKeyword)) {
                count++;
                sb.append(time).append(" ").append(user).append(": ").append(message).append("\n");
            }
        }

        return new FilterResult(count, sb.toString());
    }
}
