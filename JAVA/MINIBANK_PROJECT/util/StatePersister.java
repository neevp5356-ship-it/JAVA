package util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import model.Account;

public class StatePersister {
    public static void save(ArrayList<Account> accounts, Path file) {
        try {
            if (!Files.exists(file.getParent())) {
                Files.createDirectories(file.getParent());
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
                oos.writeObject(accounts);
            }
        } catch (Exception e) {
            System.err.println("Failed to save state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Account> load(Path file) {
        if (!Files.exists(file)) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            return (ArrayList<Account>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Failed to load state: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
