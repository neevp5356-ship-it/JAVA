import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProducerConsumer {

    // Small shared bounded buffer coordinated with wait() and notifyAll()
    static class SharedBuffer {
        private final int capacity;
        private final Queue<Integer> queue = new LinkedList<>();

        public SharedBuffer(int capacity) {
            this.capacity = capacity;
        }

        // Synchronized method for producer to add items
        public synchronized void produce(int item) throws InterruptedException {
            while (queue.size() == capacity) {
                System.out.println("  [BUFFER FULL] Producer waiting... Buffer capacity (" + capacity + ") reached.");
                wait();
            }
            queue.add(item);
            System.out.println("[PRODUCER] Produced: " + item + " | Buffer size: " + queue.size() + "/" + capacity);
            notifyAll(); // Notify consumer that new item is available
        }

        // Synchronized method for consumer to remove items
        public synchronized int consume() throws InterruptedException {
            while (queue.isEmpty()) {
                System.out.println("  [BUFFER EMPTY] Consumer waiting for new items...");
                wait();
            }
            int item = queue.poll();
            System.out.println("[CONSUMER] Consumed: " + item + " | Buffer size: " + queue.size() + "/" + capacity);
            notifyAll(); // Notify producer that space is freed up
            return item;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int bufferCapacity = 3;
        int totalItems = 10;
        SharedBuffer buffer = new SharedBuffer(bufferCapacity);
        List<Integer> consumedList = new ArrayList<>();

        System.out.println("=== 2. Producer-Consumer using wait() & notify() ===");
        System.out.println("Buffer capacity: " + bufferCapacity + ", Total items to transfer: " + totalItems + "\n");

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.produce(i);
                    Thread.sleep(100); // brief sleep between productions
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Producer interrupted");
            }
        }, "Producer-Thread");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    int item = buffer.consume();
                    consumedList.add(item);
                    Thread.sleep(200); // slightly slower consumer to demonstrate buffer filling
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumer interrupted");
            }
        }, "Consumer-Thread");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("\n--- Verification Summary ---");
        System.out.println("Total items consumed: " + consumedList.size() + " / " + totalItems);
        System.out.println("Consumed items in order: " + consumedList);

        boolean inOrder = true;
        for (int i = 0; i < totalItems; i++) {
            if (consumedList.get(i) != i + 1) {
                inOrder = false;
                break;
            }
        }

        if (inOrder && consumedList.size() == totalItems) {
            System.out.println("SUCCESS: All items produced and consumed in exact order with none lost!");
        } else {
            System.out.println("FAILURE: Order mismatch or lost items detected.");
        }
    }
}
