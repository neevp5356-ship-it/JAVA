import java.util.*;

public class VendingMachine {
    enum Coin {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {
        int snack_price = 15;
        int total = 0;
        Scanner s = new Scanner(System.in);
        System.out.println("Snack price is" + snack_price);
        while (total < snack_price) {

            System.out.print("Enter coin:");
            String input = s.next().toUpperCase();
            try {
                Coin c = Coin.valueOf(input);
                int value = 0;
                switch (c) {
                    case ONE:
                        value = 1;
                        break;
                    case TWO:
                        value = 2;
                        break;
                    case FIVE:
                        value = 5;
                        break;
                    case TEN:
                        value = 10;
                        break;
                }
                total += value;
                System.out.println("Total:" + total);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid coin");
            }

        }
        s.close();
    }
}

