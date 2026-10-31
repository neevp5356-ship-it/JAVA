import java.util.*;

@FunctionalInterface
interface DiscountRule {
    double apply(double price);
}

public class DiscountEngine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double[] prices = { 500, 1000, 1500, 2000 };
        DiscountRule tenPercent = price -> price * 0.90;
        DiscountRule twentyPercent = price -> price * 0.80;
        DiscountRule fixed100 = price -> price - 100;

        System.out.println("1. 10% discount");
        System.out.println("2. 20% discount");
        System.out.println("3. Rs.100 discount");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        DiscountRule rule;
        if (choice == 1) {
            rule = tenPercent;
        } else if (choice == 2) {
            rule = twentyPercent;
        } else {
            rule = fixed100;
        }

        System.out.println("\nPrices after discount:");
        for (double price : prices) {
            System.out.println(price + " -> " + rule.apply(price));
        }

        sc.close();
    }
}

