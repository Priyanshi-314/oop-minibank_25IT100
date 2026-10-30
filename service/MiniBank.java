package service;

import java.util.Scanner;

import model.Account;
import model.SavingsAccount;

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
            "\n=== PRACTICAL 9: MULTITHREADING ==="
        );

        Account account = new SavingsAccount(
            "Priyanshi",
            0,
            0
        );

        account.setTransactionMessagesEnabled(false);

        System.out.println("\n=== ACCOUNT DETAILS ===");

        System.out.println(
            "Account Holder : " + account.getOwnerName()
        );

        System.out.println(
            "Account Number : " + account.getAccountNumber()
        );

        System.out.println(
            "Initial Balance: Rs. " + account.getBalance()
        );

        int numberOfThreads = 10;
        int depositsPerThread = 1000;
        long depositAmount = 1;

        long expectedBalance =
                numberOfThreads * depositsPerThread * depositAmount;

        System.out.println(
            "\n=== THREAD CONFIGURATION ==="
        );

        System.out.println(
            "Number of Threads  : " + numberOfThreads
        );

        System.out.println(
            "Deposits Per Thread: " + depositsPerThread
        );

        System.out.println(
            "Amount Per Deposit : Rs. " + depositAmount
        );

        System.out.println(
            "Expected Balance   : Rs. " + expectedBalance
        );

        Thread[] threads = new Thread[numberOfThreads];

        System.out.println(
            "\n=== STARTING THREADS ==="
        );

        for (int i = 0; i < numberOfThreads; i++) {

            AccountWorker worker = new AccountWorker(
                account,
                depositsPerThread,
                depositAmount
            );

            threads[i] = new Thread(
                worker,
                "Bank-Thread-" + (i + 1)
            );

            threads[i].start();
        }

        System.out.println(
            "\n=== WAITING FOR THREADS ==="
        );

        for (Thread thread : threads) {

            try {

                thread.join();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                System.out.println(
                    "Thread interrupted."
                );

                return;
            }
        }

        System.out.println(
            "\n=== FINAL TRANSACTION RESULT ==="
        );

        System.out.println(
            "Account Holder  : " + account.getOwnerName()
        );

        System.out.println(
            "Account Number  : " + account.getAccountNumber()
        );

        System.out.println(
            "Expected Balance: Rs. " + expectedBalance
        );

        System.out.println(
            "Actual Balance  : Rs. " + account.getBalance()
        );

        System.out.println(
            "Synchronization : "
            + (account.getBalance() == expectedBalance
                ? "SUCCESSFUL"
                : "FAILED")
        );

        showMenu();
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