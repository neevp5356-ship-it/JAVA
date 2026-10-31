import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PersistentTaskService {
    private List<String> records = new ArrayList<>();

    public void addRecord(String rec) {
        records.add(rec);
    }

    public int size() {
        return records.size();
    }

    public List<String> getRecords() {
        return new ArrayList<>(records);
    }

    public void save(Path file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(records);
        }
    }

    @SuppressWarnings("unchecked")
    public void load(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            records = (List<String>) ois.readObject();
        }
    }
}

