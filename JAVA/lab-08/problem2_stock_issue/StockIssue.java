import java.util.*;

class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(String message, int shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {
        stock.put("Laptop", 5);
        stock.put("Mouse", 10);
        stock.put("Keyboard", 3);
    }

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        // Check quantity
        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero.");
        }
        if (!stock.containsKey(item)) {
            throw new OutOfStockException("Item '" + item + "' is not available.", qty);
        }
        int available = stock.get(item);
        // Check stock
        if (qty > available) {
            int shortfall = qty - available;
            throw new OutOfStockException("Not enough stock for " + item + ". Available: " + available + ", Requested: " + qty, shortfall);
        }
        stock.put(item, available - qty);
        System.out.println("Issued " + qty + " " + item + "(s) successfully.");
    }
}

public class StockIssue {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        // Requests: item and quantity
        String[][] requests = {
            {"Laptop", "2"},
            {"Mouse", "15"},
            {"Keyboard", "0"},
            {"Keyboard", "2"},
            {"Monitor", "1"},
            {"Mouse", "3"}
        };

        for (String[] request : requests) {
            String item = request[0];
            int qty = Integer.parseInt(request[1]);
            System.out.println("\nProcessing request: " + item + " - " + qty);
            try {
                warehouse.issue(item, qty);
            } catch (OutOfStockException e) {
                System.out.println("Out of stock: " + e.getMessage());
                System.out.println("Shortfall: " + e.getShortfall());
            } catch (InvalidQuantityException e) {
                System.out.println("Invalid quantity: " + e.getMessage());
            }
        }
        System.out.println("\nAll requests processed.");
    }
}

