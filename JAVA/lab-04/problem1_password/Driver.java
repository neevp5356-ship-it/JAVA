package p1_password;

public class Driver {
    public static void main(String[] args) {
        String[] testPasswords = {
            "abc",
            "password",
            "Pass1234",
            "Abcd1234!"
        };

        System.out.println("=========================================");
        System.out.println("   PASSWORD STRENGTH CHECKER DRIVER     ");
        System.out.println("=========================================\n");

        for (String pw : testPasswords) {
            System.out.println("Testing Password: \"" + pw + "\"");
            
            boolean r1 = PasswordChecker.checkLength(pw);
            boolean r2 = PasswordChecker.checkUppercase(pw);
            boolean r3 = PasswordChecker.checkDigit(pw);
            boolean r4 = PasswordChecker.checkSpecialChar(pw);

            System.out.println("  [Rule 1] Length >= 8:      " + (r1 ? "PASS [✓]" : "FAIL [✗]"));
            System.out.println("  [Rule 2] Uppercase Letter: " + (r2 ? "PASS [✓]" : "FAIL [✗]"));
            System.out.println("  [Rule 3] Digit (0-9):      " + (r3 ? "PASS [✓]" : "FAIL [✗]"));
            System.out.println("  [Rule 4] Special Character:" + (r4 ? "PASS [✓]" : "FAIL [✗]"));
            System.out.println("  -> Overall Strength:       " + PasswordChecker.strength(pw));
            System.out.println("-----------------------------------------");
        }
    }
}
