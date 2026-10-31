import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class DeadlockDemo {

    // Simple Account resource with an ID and balance
    static class Account {
        final int id;
        final String name;
        int balance;

        Account(int id, String name, int balance) {
            this.id = id;
            this.name = name;
            this.balance = balance;
        }
    }

    // --- Scenario 1: Deadlock Reproduction (Opposite Lock Ordering) ---
    static void runDeadlockScenario(boolean hangForever) {
        System.out.println("=================================================");
        System.out.println(" SCENARIO 1: REPRODUCING DEADLOCK (Opposite Order)");
        System.out.println("=================================================");
        System.out.println("Thread 1 locks Account 1, then wants Account 2.");
        System.out.println("Thread 2 locks Account 2, then wants Account 1.\n");

        Account acc1 = new Account(1, "Account-Alice", 1000);
        Account acc2 = new Account(2, "Account-Bob", 2000);

        Thread t1 = new Thread(() -> {
            synchronized (acc1) {
                System.out.println("[Thread 1] Acquired lock on " + acc1.name);
                try {
                    // Sleep to ensure Thread 2 acquires lock on acc2
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}

                System.out.println("[Thread 1] Attempting to acquire lock on " + acc2.name + "...");
                synchronized (acc2) {
                    System.out.println("[Thread 1] Acquired lock on " + acc2.name + ". Transferring money...");
                    acc1.balance -= 100;
                    acc2.balance += 100;
                }
            }
        }, "Deadlock-Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (acc2) {
                System.out.println("[Thread 2] Acquired lock on " + acc2.name);
                try {
                    // Sleep to ensure Thread 1 acquires lock on acc1
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}

                System.out.println("[Thread 2] Attempting to acquire lock on " + acc1.name + "...");
                synchronized (acc1) {
                    System.out.println("[Thread 2] Acquired lock on " + acc1.name + ". Transferring money...");
                    acc2.balance -= 200;
                    acc1.balance += 200;
                }
            }
        }, "Deadlock-Thread-2");

        if (!hangForever) {
            t1.setDaemon(true);
            t2.setDaemon(true);
        }

        t1.start();
        t2.start();

        if (hangForever) {
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            // Wait briefly and verify deadlock using JVM's ThreadMXBean
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            long[] deadlockedThreadIds = bean.findDeadlockedThreads();

            if (deadlockedThreadIds != null && deadlockedThreadIds.length > 0) {
                System.out.println("\n>>> DEADLOCK CONFIRMED by JVM ThreadMXBean! <<<");
                for (long id : deadlockedThreadIds) {
                    ThreadInfo info = bean.getThreadInfo(id);
                    System.out.println("  - " + info.getThreadName() + " is waiting on lock held by " + info.getLockOwnerName());
                }
                System.out.println("Both threads are in circular wait and can never proceed.\n");
            }
        }
    }

    // Safe transfer helper using consistent lock ordering
    static Runnable makeTransfer(Account from, Account to, int amount, String threadName) {
        return () -> {
            // Determine order of locks based on unique ID
            Account firstLock = from.id < to.id ? from : to;
            Account secondLock = from.id < to.id ? to : from;

            System.out.println("[" + threadName + "] Acquiring first lock on " + firstLock.name + " (ID: " + firstLock.id + ")...");
            synchronized (firstLock) {
                System.out.println("[" + threadName + "] Acquired " + firstLock.name + ". Now acquiring second lock on " + secondLock.name + " (ID: " + secondLock.id + ")...");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {}

                synchronized (secondLock) {
                    System.out.println("[" + threadName + "] Acquired both locks! Transferring $" + amount + " from " + from.name + " to " + to.name);
                    from.balance -= amount;
                    to.balance += amount;
                }
            }
            System.out.println("[" + threadName + "] Transfer completed and all locks released.");
        };
    }

    // --- Scenario 2: Deadlock Removed (Consistent Lock Ordering) ---
    static void runResolvedScenario() {
        System.out.println("=================================================");
        System.out.println(" SCENARIO 2: DEADLOCK REMOVED (Consistent Order)");
        System.out.println("=================================================");
        System.out.println("Both threads acquire locks in a globally consistent order (e.g., lower Account ID first).\n");

        Account acc1 = new Account(1, "Account-Alice", 1000);
        Account acc2 = new Account(2, "Account-Bob", 2000);

        Thread t1 = new Thread(makeTransfer(acc1, acc2, 100, "Safe-Thread-1"), "Safe-Thread-1");
        Thread t2 = new Thread(makeTransfer(acc2, acc1, 200, "Safe-Thread-2"), "Safe-Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nFinal Balances:");
        System.out.println(acc1.name + ": $" + acc1.balance);
        System.out.println(acc2.name + ": $" + acc2.balance);
        System.out.println("SUCCESS: Both transfers finished cleanly with zero deadlock!\n");
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("deadlock")) {
            // Pure hanging deadlock for manual inspection
            runDeadlockScenario(true);
        } else if (args.length > 0 && args[0].equalsIgnoreCase("resolved")) {
            // Only resolved scenario
            runResolvedScenario();
        } else {
            // Default: demonstrate deadlock detection followed by resolved solution
            runDeadlockScenario(false);
            runResolvedScenario();
            System.out.println("Tip: Run 'java DeadlockDemo deadlock' to observe a pure hanging deadlock.");
            System.out.println("Tip: Run 'java DeadlockDemo resolved' to run only the resolved scenario.");
        }
    }
}
