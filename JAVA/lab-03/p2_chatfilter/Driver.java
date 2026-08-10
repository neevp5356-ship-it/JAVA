package p2_chatfilter;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        String[] logLines = {
            "10:05 alice Hello there",
            "10:06 bob General Kenobi!",
            "10:07 malformed_line_without_message",
            "10:09 alice hello again everyone",
            "10:12 charlie Good morning"
        };

        String keyword = "hello";

        System.out.println("=========================================");
        System.out.println("        CHAT-LOG FILTER DRIVER          ");
        System.out.println("=========================================\n");

        if (args.length > 0) {
            keyword = args[0];
        } else {
            System.out.println("Logs available for filtering:");
            for (String log : logLines) {
                System.out.println("  > " + log);
            }
            System.out.println();
            
            System.out.print("Enter search keyword (default 'hello'): ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    keyword = input;
                }
            }
        }

        System.out.println("\nFiltering logs for keyword: \"" + keyword + "\"...\n");

        ChatFilter.FilterResult result = ChatFilter.filterLogs(logLines, keyword);

        System.out.println("Matches: " + result.getMatchCount());
        System.out.print(result.getReport());
    }
}
