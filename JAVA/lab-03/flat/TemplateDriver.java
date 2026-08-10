public class TemplateDriver {
    public static void main(String[] args) {
        String template = "Dear {name}, order {id} ships {date}.";
        String[] names = {"name", "id"};
        String[] values = {"Riya", "A07"};

        System.out.println("=== TEMPLATE FILLER ===");
        System.out.println("Template: " + template);

        String filled = TemplateFiller.fillTemplate(template, names, values);
        System.out.println("Result:   " + filled);
    }
}
