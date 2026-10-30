package service;

import java.util.Scanner;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;

import exception.BankException;
import exception.InvalidAmountException;

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

public class MiniBank {

    public static void main(String[] args) {

        BankInfo bank = new BankInfo(
            "MiniBank",
            "Main Branch"
        );

        System.out.println(
            "=== " + bank.name()
            + " | " + bank.branch() + " ==="
        );

        System.out.println(
            "\n=== PRACTICAL 10: CONCURRENT TRANSACTIONS ==="
        );

        Account savings = new SavingsAccount(
            "Priyanshi", 5000, 1000
        );

        Account current = new CurrentAccount(
            "Rahul", 3000, 2000
        );

        savings.setTransactionMessagesEnabled(false);
        current.setTransactionMessagesEnabled(false);

        System.out.println("\n=== ACCOUNT DETAILS ===");

        printAccount(savings);
        printAccount(current);

        // 1. THREAD POOL

        System.out.println(
            "\n1. THREAD POOL TRANSACTION PROCESSING"
        );

        TransactionProcessor processor =
                new TransactionProcessor();

        // Five deposits of Rs. 100
        for (int i = 0; i < 5; i++) {

            processor.submit(() -> {

                try {

                    savings.deposit(100);

                    System.out.println(
                        Thread.currentThread().getName()
                        + " deposited Rs. 100"
                    );

                } catch (InvalidAmountException e) {

                    System.out.println(
                        "Error: " + e.getMessage()
                    );
                }
            });
        }

        // Three withdrawals of Rs. 50
        for (int i = 0; i < 3; i++) {

            processor.submit(() -> {

                try {

                    savings.withdraw(50);

                    System.out.println(
                        Thread.currentThread().getName()
                        + " withdrew Rs. 50"
                    );

                } catch (BankException e) {

                    System.out.println(
                        "Error: " + e.getMessage()
                    );
                }
            });
        }

        processor.stop();

        System.out.println(
            "Balance after thread pool: Rs. "
            + savings.getBalance()
        );

        // 2. PRODUCER-CONSUMER

        System.out.println(
            "\n2. PRODUCER-CONSUMER DEMONSTRATION"
        );

        TransactionBuffer buffer =
                new TransactionBuffer(3);

        Thread producer = new Thread(() -> {

            try {

                for (int i = 0; i < 5; i++) {

                    buffer.produce(() -> {

                        try {

                            current.deposit(200);

                            System.out.println(
                                "Consumer deposited Rs. 200"
                            );

                        } catch (InvalidAmountException e) {

                            System.out.println(
                                "Error: " + e.getMessage()
                            );
                        }
                    });
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

            } finally {

                buffer.close();
            }

        }, "Producer");

        Thread consumer = new Thread(() -> {

            try {

                Runnable transaction;

                while ((transaction = buffer.consume()) != null) {

                    transaction.run();
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }

        }, "Consumer");

        producer.start();
        consumer.start();

        try {

            producer.join();
            consumer.join();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            return;
        }

        System.out.println(
            "Balance after producer-consumer: Rs. "
            + current.getBalance()
        );

        // 3. DEADLOCK DEMONSTRATION

        DeadlockDemo.demonstrate();

        // 4. SAFE CONCURRENT TRANSFERS

        System.out.println(
            "\n3. DEADLOCK-FREE CONCURRENT TRANSFERS"
        );

        Thread transfer1 = new Thread(() -> {

            try {

                savings.transfer(current, 500);

                System.out.println(
                    "Transfer A to B successful."
                );

            } catch (BankException e) {

                System.out.println(
                    "Transfer Error: " + e.getMessage()
                );
            }

        }, "Transfer-Thread-1");

        Thread transfer2 = new Thread(() -> {

            try {

                current.transfer(savings, 300);

                System.out.println(
                    "Transfer B to A successful."
                );

            } catch (BankException e) {

                System.out.println(
                    "Transfer Error: " + e.getMessage()
                );
            }

        }, "Transfer-Thread-2");

        transfer1.start();
        transfer2.start();

        try {

            transfer1.join();
            transfer2.join();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            return;
        }

        System.out.println(
            "Both transfer threads completed."
        );

        // 5. FINAL BALANCES

        System.out.println(
            "\n=== FINAL ACCOUNT DETAILS ==="
        );

        printAccount(savings);
        printAccount(current);

        showMenu();
    }

    private static void printAccount(Account account) {

        System.out.println("------------------------------");

        System.out.println(
            "Account Holder : " + account.getOwnerName()
        );

        System.out.println(
            "Account Number : " + account.getAccountNumber()
        );

        System.out.println(
            "Account Type   : "
            + account.getClass().getSimpleName()
        );

        System.out.println(
            "Balance        : Rs. " + account.getBalance()
        );
    }

    private static void showMenu() {

        try (Scanner scanner = new Scanner(System.in)) {

            boolean running = true;

            while (running) {

                System.out.println("\n=== MINIBANK MENU ===");

                System.out.println("1. Open Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. Exit");

                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {

                    System.out.println("Invalid input.");
                    scanner.next();
                    continue;
                }

                int choice = scanner.nextInt();

                MenuOption option = switch (choice) {

                    case 1 -> MenuOption.OPEN_ACCOUNT;
                    case 2 -> MenuOption.DEPOSIT;
                    case 3 -> MenuOption.WITHDRAW;
                    case 4 -> MenuOption.TRANSFER;
                    case 5 -> MenuOption.EXIT;
                    default -> null;
                };

                if (option == null) {

                    System.out.println("Invalid choice.");
                    continue;
                }

                String message = switch (option) {

                    case OPEN_ACCOUNT ->
                        "Open Account - interactive feature coming soon.";

                    case DEPOSIT ->
                        "Deposit - interactive feature coming soon.";

                    case WITHDRAW ->
                        "Withdraw - interactive feature coming soon.";

                    case TRANSFER ->
                        "Transfer - interactive feature coming soon.";

                    case EXIT ->
                        "Thank you for using MiniBank. Goodbye!";
                };

                System.out.println(message);

                if (option == MenuOption.EXIT) {
                    running = false;
                }
            }
        }
    }
}