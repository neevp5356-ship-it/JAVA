import java.io.*;

class StudentRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentId;
    private String name;
    private transient String secretPin; // transient field: will not be saved

    public StudentRecord(String studentId, String name, String secretPin) {
        this.studentId = studentId;
        this.name = name;
        this.secretPin = secretPin;
    }

    @Override
    public String toString() {
        return "StudentRecord{id='" + studentId + "', name='" + name + "', secretPin='" + secretPin + "'}";
    }
}

public class SaveLoadDemo {
    public static void main(String[] args) {
        String filename = "students.dat";
        StudentRecord[] original = {
            new StudentRecord("25AIML055", "Neev Patel", "9988"),
            new StudentRecord("25AIML012", "Riya Shah", "4455")
        };

        // Serialize array to disk
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(original);
            System.out.println("--- Saved Original Objects ---");
            for (StudentRecord r : original) {
                System.out.println(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            StudentRecord[] loaded = (StudentRecord[]) ois.readObject();
            System.out.println("\n--- Loaded Objects from File (transient is null) ---");
            for (StudentRecord r : loaded) {
                System.out.println(r);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

