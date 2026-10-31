package JAVA.lab_05.problem1_calculator;

import java.util.Scanner;

public class GuardedCalculator {
    public static double calculate(double a, double b, char operator) throws DivideByZeroException {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new DivideByZeroException("Cannot divide by zero.");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean success = false;
        int attempt = 0;

        while (!success) {
            attempt++;
            try {
                System.out.println("\nEnter first number:");
                double num1 = Double.parseDouble(sc.nextLine());
                System.out.println("Enter operator (+, -, *, /):");
                char operator = sc.nextLine().charAt(0);
                System.out.println("Enter second number:");
                double num2 = Double.parseDouble(sc.nextLine());
                double result = calculate(num1, num2, operator);
                System.out.println("Result = " + result);
                success = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number input. Please enter valid numbers.");
            } catch (DivideByZeroException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("Attempt logged (" + attempt + ").");
            }
        }
        sc.close();
        System.out.println("Calculation completed successfully.");
    }
}
