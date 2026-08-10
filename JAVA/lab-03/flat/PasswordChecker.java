public class PasswordChecker {

    public static boolean checkLength(String pw) {
        return pw != null && pw.length() >= 8;
    }

    public static boolean checkUppercase(String pw) {
        return pw != null && pw.matches(".*[A-Z].*");
    }

    public static boolean checkDigit(String pw) {
        return pw != null && pw.matches(".*[0-9].*");
    }

    public static boolean checkSpecialChar(String pw) {
        return pw != null && pw.matches(".*[^a-zA-Z0-9].*");
    }

    public static int countPassedRules(String pw) {
        int count = 0;
        if (checkLength(pw)) count++;
        if (checkUppercase(pw)) count++;
        if (checkDigit(pw)) count++;
        if (checkSpecialChar(pw)) count++;
        return count;
    }

    public static String strength(String pw) {
        int score = countPassedRules(pw);
        if (score <= 1) {
            return "Weak";
        } else if (score <= 3) {
            return "Medium";
        } else {
            return "Strong";
        }
    }
}
