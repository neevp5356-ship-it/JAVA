public class CounterRace {
    static int count = 0;

    static void incrementUnsync() {
        count++;
    }

    static synchronized void incrementSync() {
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        int numThreads = 5;
        int incrementsPerThread = 100000;
        int expected = numThreads * incrementsPerThread;

        System.out.println("--- Without Synchronization ---");
        count = 0;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    incrementUnsync();
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Expected count: " + expected);
        System.out.println("Actual count: " + count);

        System.out.println("\n--- With Synchronization ---");
        count = 0;
        threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    incrementSync();
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Expected count: " + expected);
        System.out.println("Actual count: " + count);
    }
}
