package p3_templatefiller;

public class Driver {
    public static void main(String[] args) {
        String template = "Dear {name}, order {id} ships {date}.";
        
        String[] names = {"name", "id"};
        String[] values = {"Riya", "A07"};

        System.out.println("=========================================");
        System.out.println("        TEMPLATE FILLER DRIVER          ");
        System.out.println("=========================================\n");

        System.out.println("Original Template:");
        System.out.println("  " + template);
        System.out.println();

        System.out.println("Supplied Parallel Arrays:");
        System.out.print("  Names:  [");
        for (int i = 0; i < names.length; i++) {
            System.out.print(names[i] + (i < names.length - 1 ? ", " : ""));
        }
        System.out.println("]");

        System.out.print("  Values: [");
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + (i < values.length - 1 ? ", " : ""));
        }
        System.out.println("]\n");

        String output = TemplateFiller.fillTemplate(template, names, values);

        System.out.println("Result:");
        System.out.println("  " + output);
    }
}
