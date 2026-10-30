package service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DeadlockDemo {

    public static void demonstrate() {

        System.out.println(
            "\n=== DEADLOCK DEMONSTRATION ==="
        );

        Object accountA = new Object();
        Object accountB = new Object();

        CountDownLatch firstLocksAcquired =
                new CountDownLatch(2);

        Runnable transferAB = () -> {

            synchronized (accountA) {

                firstLocksAcquired.countDown();

                awaitLatch(firstLocksAcquired);

                synchronized (accountB) {

                    System.out.println(
                        "Transfer A to B completed."
                    );
                }
            }
        };

        Runnable transferBA = () -> {

            synchronized (accountB) {

                firstLocksAcquired.countDown();

                awaitLatch(firstLocksAcquired);

                synchronized (accountA) {

                    System.out.println(
                        "Transfer B to A completed."
                    );
                }
            }
        };

        Thread thread1 = new Thread(
            transferAB,
            "Deadlock-Thread-1"
        );

        Thread thread2 = new Thread(
            transferBA,
            "Deadlock-Thread-2"
        );

        // Daemon threads will not prevent the application from exiting.
        thread1.setDaemon(true);
        thread2.setDaemon(true);

        thread1.start();
        thread2.start();

        try {

            thread1.join(1000);
            thread2.join(1000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            return;
        }

        if (thread1.isAlive() && thread2.isAlive()) {

            System.out.println(
                "Deadlock detected: Both threads are blocked."
            );

            System.out.println(
                "Cause: Opposite account lock ordering."
            );
        }

        System.out.println(
            "Solution: Lock accounts in ascending account-number order."
        );
    }

    private static void awaitLatch(CountDownLatch latch) {

        try {

            latch.await(2, TimeUnit.SECONDS);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}