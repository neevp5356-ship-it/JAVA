import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// @NotBlank annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

// @MaxLength annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

// SignupForm class
class SignupForm {
    @NotBlank
    @MaxLength(20)
    String name;

    @NotBlank
    @MaxLength(30)
    String email;

    @NotBlank
    @MaxLength(15)
    String password;

    SignupForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

// Validator class
class FormChecker {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                String text = value == null ? "" : value.toString();

                // Check @NotBlank
                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (text.trim().isEmpty()) {
                        errors.add(field.getName() + " must not be blank");
                    }
                }

                // Check @MaxLength
                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength annotation = field.getAnnotation(MaxLength.class);
                    int max = annotation.value();
                    if (text.length() > max) {
                        errors.add(field.getName() + " must not exceed " + max + " characters");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Error reading field: " + field.getName());
            }
        }
        return errors;
    }
}

// Main class
public class FormValidator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== Signup Form =====");
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        SignupForm form = new SignupForm(name, email, password);
        List<String> errors = FormChecker.validate(form);

        System.out.println("\n===== Validation Result =====");
        if (errors.isEmpty()) {
            System.out.println("Form is valid!");
        } else {
            System.out.println("Form has errors:");
            for (String error : errors) {
                System.out.println("- " + error);
            }
        }
        sc.close();
    }
}

