class DatabaseConnection implements AutoCloseable {
    public DatabaseConnection() {
        System.out.println("Database connection opened.");
    }

    public void execute() {
        System.out.println("Executing database operation...");
        throw new RuntimeException("Original database error!");
    }

    @Override
    public void close() {
        System.out.println("Database connection closed.");
    }
}

public class AutoCloseableDemo {
    public static void main(String[] args) {
        try (DatabaseConnection db = new DatabaseConnection()) {
            System.out.println("Inside try block.");
            // This throws an exception
            db.execute();
        } catch (RuntimeException e) {
            System.out.println("Caught error: " + e.getMessage());
        }
        System.out.println("Program continues normally.");
    }
}

