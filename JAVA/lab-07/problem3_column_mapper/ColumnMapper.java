import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.Scanner;

// Column annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

// Student class
class Student {
    @Column(name = "id")
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "age")
    int age;

    public void display() {
        System.out.println("\n===== Student Details =====");
        System.out.println("ID : " + id);
        System.out.println("Name : " + name);
        System.out.println("Email : " + email);
        System.out.println("Age : " + age);
    }
}

// Mapper class
class ColumnMapperUtil {
    public static void populate(Object object, String[] headers, String[] data) throws Exception {
        Class<?> clazz = object.getClass();

        // Check each field
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            String columnName = column.name();

            // Find matching header
            int index = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(columnName)) {
                    index = i;
                    break;
                }
            }

            // Missing column handling
            if (index == -1) {
                System.out.println("Missing column: " + columnName);
                continue;
            }

            // Make field accessible
            field.setAccessible(true);
            String value = data[index];

            // Convert data according to field type
            if (field.getType() == int.class) {
                field.setInt(object, Integer.parseInt(value));
            } else if (field.getType() == String.class) {
                field.set(object, value);
            }
        }
    }
}

// Main class
public class ColumnMapper {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== Column Mapper =====");

        // Header input
        System.out.print("Enter headers separated by comma: ");
        String headerInput = sc.nextLine();
        String[] headers = headerInput.split(",");

        // Data input
        System.out.print("Enter data separated by comma: ");
        String dataInput = sc.nextLine();
        String[] data = dataInput.split(",");

        // Check same number of values
        if (headers.length != data.length) {
            System.out.println("Error: Number of headers and data values must match.");
            sc.close();
            return;
        }

        try {
            Student student = new Student();
            // Populate object using reflection
            ColumnMapperUtil.populate(student, headers, data);
            student.display();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}

