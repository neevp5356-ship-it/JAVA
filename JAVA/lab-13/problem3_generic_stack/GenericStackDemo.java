import java.util.EmptyStackException;

class ArrayStack<T> {
    private final Object[] store;
    private int top = 0;

    public ArrayStack(int capacity) {
        this.store = new Object[capacity];
    }

    public void push(T val) {
        if (top == store.length) {
            throw new IllegalStateException("Stack Overflow: capacity reached (" + store.length + ")");
        }
        store[top++] = val;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = (T) store[--top];
        store[top] = null; // Clean reference for GC
        return item;
    }

    public boolean isEmpty() {
        return top == 0;
    }
}

public class GenericStackDemo {
    public static void main(String[] args) {
        ArrayStack<String> names = new ArrayStack<>(4);
        names.push("Alpha");
        names.push("Beta");
        System.out.println("Popped: " + names.pop());
        System.out.println("Popped: " + names.pop());
        try {
            names.pop();
        } catch (EmptyStackException e) {
            System.out.println("Defensive Check: Caught EmptyStackException safely.");
        }

        ArrayStack<Double> metrics = new ArrayStack<>(3);
        metrics.push(3.1415);
        metrics.push(2.7182);
        System.out.println("Popped Double: " + metrics.pop());
    }
}

