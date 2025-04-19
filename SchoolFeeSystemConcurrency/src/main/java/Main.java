public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Simulate 5 parents paying concurrently
        Thread[] payments = new Thread[5];
        for (int i = 1; i <= 5; i++) {
    payments[i-1] = new PaymentThread(i, 100 * i); // Ensure amount = 100, 200, 300, etc.
    payments[i-1].start();
    Thread.sleep(100); // Increased delay for better ordering
}

        // Admin generates report after 2 payments (approx)
        Thread adminThread = new Thread(() -> {
            try {
                Thread.sleep(250); // Wait for some payments to complete
                FeeDatabase.generateReport();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        adminThread.start();

        // Wait for all threads to finish
        for (Thread t : payments) t.join();
        adminThread.join();
    }
}