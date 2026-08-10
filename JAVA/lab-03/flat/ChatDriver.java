import java.util.Scanner;

public class ChatDriver {
    public static void main(String[] args) {
        String[] logLines = {
            "10:05 alice Hello there",
            "10:06 bob General Kenobi!",
            "10:07 malformed_line",
            "10:09 alice hello again everyone",
            "10:12 charlie Good morning"
        };

        String keyword = "hello";
        
        if (args.length > 0) {
            keyword = args[0];
        }

        System.out.println("=== CHAT LOG FILTER ===");
        System.out.println("Keyword: \"" + keyword + "\"\n");

        ChatFilter.FilterResult result = ChatFilter.filterLogs(logLines, keyword);

        System.out.println("Matches: " + result.getMatchCount());
        System.out.print(result.getReport());
    }
}
