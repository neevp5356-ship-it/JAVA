public class BoundedStats {
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) return 0.0;
        double total = 0.0;
        for (N val : values) {
            total += val.doubleValue(); // Safe invocation guaranteed by bounded type
        }
        return total / values.length;
    }

    public static void main(String[] args) {
        Integer[] intArr = { 10, 20, 30, 40 };
        Double[] dblArr = { 2.5, 3.5, 4.5 };
        Long[] lngArr = { 100L, 200L, 300L };

        System.out.printf("Integer[] Average: %.2f%n", average(intArr));
        System.out.printf("Double[] Average: %.2f%n", average(dblArr));
        System.out.printf("Long[] Average: %.2f%n", average(lngArr));
    }
}

