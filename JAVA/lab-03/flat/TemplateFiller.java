import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateFiller {

    public static String fillTemplate(String template, String[] names, String[] values) {
        if (template == null) return "";

        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(template);

        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = lookupValue(key, names, values);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String lookupValue(String key, String[] names, String[] values) {
        if (names != null && values != null) {
            for (int i = 0; i < names.length; i++) {
                if (i < values.length && names[i].equalsIgnoreCase(key)) {
                    return values[i];
                }
            }
        }
        return "[?]";
    }
}
