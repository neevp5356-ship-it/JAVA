package p1_password;

/**
 * PasswordChecker checks passwords against 4 security rules:
 * 1. Minimum length of 8 characters.
 * 2. Contains at least one uppercase letter.
 * 3. Contains at least one digit.
 * 4. Contains at least one special character.
 */
public class PasswordChecker {

    // Rule 1: Length >= 8
    public static boolean checkLength(String pw) {
        return pw != null && pw.length() >= 8;
    }

    // Rule 2: Contains uppercase letter [A-Z]
    public static boolean checkUppercase(String pw) {
        return pw != null && pw.matches(".*[A-Z].*");
    }

    // Rule 3: Contains digit [0-9]
    public static boolean checkDigit(String pw) {
        return pw != null && pw.matches(".*[0-9].*");
    }

    // Rule 4: Contains special character (non-alphanumeric)
    public static boolean checkSpecialChar(String pw) {
        return pw != null && pw.matches(".*[^a-zA-Z0-9].*");
    }

    // Counts how many rules pass (0 to 4)
    public static int countPassedRules(String pw) {
        int count = 0;
        if (checkLength(pw)) count++;
        if (checkUppercase(pw)) count++;
        if (checkDigit(pw)) count++;
        if (checkSpecialChar(pw)) count++;
        return count;
    }

    // Returns strength label: "Weak" (0-1), "Medium" (2-3), "Strong" (4)
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
