public class PasswordDriver {
    public static void main(String[] args) {
        String[] testPasswords = {
            "abc",
            "password",
            "Pass1234",
            "Abcd1234!"
        };

        System.out.println("=== PASSWORD STRENGTH CHECKER ===");
        for (String pw : testPasswords) {
            System.out.println("Password: \"" + pw + "\"");
            System.out.println("  Length >= 8:      " + PasswordChecker.checkLength(pw));
            System.out.println("  Uppercase Letter: " + PasswordChecker.checkUppercase(pw));
            System.out.println("  Digit:            " + PasswordChecker.checkDigit(pw));
            System.out.println("  Special Char:     " + PasswordChecker.checkSpecialChar(pw));
            System.out.println("  Final Label:      " + PasswordChecker.strength(pw));
            System.out.println();
        }
    }
}
