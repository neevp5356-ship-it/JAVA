public class ArraySumRace {
    static int size = 10000000;
    static int[] arr = new int[size];
    static int numThreads = 4;
    static long totalSum = 0;
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long expectedSum = size;

        System.out.println("Array size: " + size);
        System.out.println("Expected sum: " + expectedSum);
        System.out.println("Number of threads: " + numThreads);

        totalSum = 0;
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[numThreads];
        int chunkSize = size / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int startIdx = i * chunkSize;
            int endIdx = (i == numThreads - 1) ? size : startIdx + chunkSize;
            threads[i] = new Thread(() -> {
                for (int j = startIdx; j < endIdx; j++) {
                    totalSum += arr[j];
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("\n1. Without Synchronization:");
        System.out.println("Result: " + totalSum);
        System.out.println("Time: " + time + " ms");

        totalSum = 0;
        start = System.currentTimeMillis();
        threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int startIdx = i * chunkSize;
            int endIdx = (i == numThreads - 1) ? size : startIdx + chunkSize;
            threads[i] = new Thread(() -> {
                for (int j = startIdx; j < endIdx; j++) {
                    synchronized (lock) {
                        totalSum += arr[j];
                    }
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        time = System.currentTimeMillis() - start;
        System.out.println("\n2. Fix 1 (Synchronized on each element):");
        System.out.println("Result: " + totalSum);
        System.out.println("Time: " + time + " ms");

        totalSum = 0;
        start = System.currentTimeMillis();
        long[] localSums = new long[numThreads];
        threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int threadId = i;
            int startIdx = i * chunkSize;
            int endIdx = (i == numThreads - 1) ? size : startIdx + chunkSize;
            threads[i] = new Thread(() -> {
                long sum = 0;
                for (int j = startIdx; j < endIdx; j++) {
                    sum += arr[j];
                }
                localSums[threadId] = sum;
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        for (long s : localSums) {
            totalSum += s;
        }
        time = System.currentTimeMillis() - start;
        System.out.println("\n3. Fix 2 (Thread-local sum):");
        System.out.println("Result: " + totalSum);
        System.out.println("Time: " + time + " ms");
    }
}
