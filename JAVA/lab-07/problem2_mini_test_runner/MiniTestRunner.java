import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Scanner;

// @Run annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

// Class containing test methods
class MyTests {
    @Run
    public void testAddition() {
        System.out.println("Test Addition: PASSED");
    }

    @Run
    public void testSubtraction() {
        System.out.println("Test Subtraction: PASSED");
    }

    public void normalMethod() {
        System.out.println("This method should NOT run");
    }

    @Run
    public void testMultiplication() {
        System.out.println("Test Multiplication: PASSED");
    }

    public void anotherMethod() {
        System.out.println("This method should NOT run");
    }
}

// Mini test runner
public class MiniTestRunner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter class name to run tests: ");
        String className = sc.nextLine();

        try {
            // Find class using reflection
            Class<?> clazz = Class.forName(className);

            // Create object
            Object object = clazz.getDeclaredConstructor().newInstance();
            int count = 0;

            System.out.println("\n===== Running Tests =====");
            // Find all methods
            for (Method method : clazz.getDeclaredMethods()) {
                // Check @Run annotation
                if (method.isAnnotationPresent(Run.class) && method.getParameterCount() == 0) {
                    System.out.println("Running: " + method.getName());
                    // Invoke method
                    method.invoke(object);
                    count++;
                }
            }
            System.out.println("\n===== Test Result =====");
            System.out.println("Total @Run methods executed: " + count);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}

