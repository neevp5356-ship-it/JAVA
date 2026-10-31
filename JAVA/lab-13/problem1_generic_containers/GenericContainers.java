class Box<T> {
    private T item;
    public Box(T item) { this.item = item; }
    public T get() { return item; }
    public void set(T item) { this.item = item; }
}

class Pair<A, B> {
    private final A key;
    private final B value;

    public Pair(A key, B value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}

public class GenericContainers {
    public static <E> E firstElement(E[] arr) {
        if (arr == null || arr.length == 0) return null;
        return arr[0];
    }

    public static void main(String[] args) {
        Box<String> stringBox = new Box<>("CHARUSAT University");
        Box<Integer> yearBox = new Box<>(2026);

        System.out.println("String Box value: " + stringBox.get());
        System.out.println("Integer Box value: " + yearBox.get());

        Pair<String, Double> grade = new Pair<>("25AIML055", 9.45);
        System.out.println("Student Grade Pair: " + grade);

        String[] devStack = {"Java", "Python", "C++"};
        Integer[] codes = {101, 202, 303};

        System.out.println("First element (String array): " + firstElement(devStack));
        System.out.println("First element (Integer array): " + firstElement(codes));
    }
}

