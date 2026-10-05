public class SeatBookingRace {
    static int seatsLeft = 5;
    static int successfulBookings = 0;

    static void bookUnsync(String user) {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seatsLeft--;
            successfulBookings++;
            System.out.println(user + " successfully booked a seat. Remaining: " + seatsLeft);
        } else {
            System.out.println(user + " failed to book. Sold out.");
        }
    }

    static synchronized void bookSync(String user) {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seatsLeft--;
            successfulBookings++;
            System.out.println(user + " successfully booked a seat. Remaining: " + seatsLeft);
        } else {
            System.out.println(user + " failed to book. Sold out.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int totalUsers = 10;

        System.out.println("--- Without Synchronization ---");
        seatsLeft = 5;
        successfulBookings = 0;
        Thread[] threads = new Thread[totalUsers];
        for (int i = 0; i < totalUsers; i++) {
            String name = "User-" + (i + 1);
            threads[i] = new Thread(() -> bookUnsync(name));
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Total successful bookings: " + successfulBookings);
        System.out.println("Remaining seats: " + seatsLeft);

        System.out.println("\n--- With Synchronization ---");
        seatsLeft = 5;
        successfulBookings = 0;
        threads = new Thread[totalUsers];
        for (int i = 0; i < totalUsers; i++) {
            String name = "User-" + (i + 1);
            threads[i] = new Thread(() -> bookSync(name));
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Total successful bookings: " + successfulBookings);
        System.out.println("Remaining seats: " + seatsLeft);
    }
}
