package service;

import java.util.Scanner;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;

// Bank information
record BankInfo(String name, String branch) {}

// Menu options
enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

public class MiniBank {

    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        BankInfo bank = new BankInfo(
            "MiniBank",
            "Main Branch"
        );

        System.out.println(
            "=== " + bank.name()
            + " | " + bank.branch() + " ==="
        );

        // PRACTICAL 12 DEMONSTRATION
        practical12Demo();

        // EXISTING MINIBANK MENU
        showMenu();
    }

    // ==========================================
    // PRACTICAL 12: COLLECTIONS FRAMEWORK
    // ==========================================

    private static void practical12Demo() {

        System.out.println(
            "\n=== PRACTICAL 12: COLLECTIONS FRAMEWORK ==="
        );

        // STEP 1: CREATE ACCOUNT STORE

        AccountStore store = new AccountStore();

        // STEP 2: CREATE ACCOUNTS

        Account savings = new SavingsAccount(
            "Priyanshi",
            5000,
            1000
        );

        Account current = new CurrentAccount(
            "Rahul",
            2000,
            1000
        );

        Account fixedDeposit = new FixedDepositAccount(
            "Anjali",
            9000
        );

        // STEP 3: ADD ACCOUNTS TO HASHMAP

        store.add(savings);
        store.add(current);
        store.add(fixedDeposit);

        System.out.println(
            "\n=== ACCOUNTS ADDED TO HASHMAP ==="
        );

        System.out.println(
            "Total Accounts: " + store.getAccountCount()
        );

        // STEP 4: DISPLAY ALL ACCOUNTS

        System.out.println(
            "\n=== ALL ACCOUNTS ==="
        );

        for (Account account : store.getAll()) {

            System.out.println(
                account.getAccountNumber()
                + " | "
                + account.getOwnerName()
                + " | Rs. "
                + account.getBalance()
            );
        }

        // STEP 5: SEARCH ACCOUNT

        System.out.println(
            "\n=== SEARCH ACCOUNT BY NUMBER ==="
        );

        String searchNumber = current.getAccountNumber();

        Account found = store.get(searchNumber);

        if (found != null) {

            System.out.println(
                "Account Found Successfully!"
            );

            System.out.println(
                "Account Number : "
                + found.getAccountNumber()
            );

            System.out.println(
                "Account Holder : "
                + found.getOwnerName()
            );

            System.out.println(
                "Balance        : Rs. "
                + found.getBalance()
            );

        } else {

            System.out.println(
                "Account not found."
            );
        }

        // STEP 6: SORT ACCOUNTS BY BALANCE

        System.out.println(
            "\n=== ACCOUNTS SORTED BY BALANCE ==="
        );

        for (Account account : store.sortedByBalance()) {

            System.out.println(
                account.getAccountNumber()
                + " | "
                + account.getOwnerName()
                + " | Rs. "
                + account.getBalance()
            );
        }

        // STEP 7: STORE TRANSACTION HISTORY

        store.addTransaction(
            "DEPOSIT " + savings.getAccountNumber() + " 500"
        );

        store.addTransaction(
            "WITHDRAW " + current.getAccountNumber() + " 200"
        );

        store.addTransaction(
            "DEPOSIT " + fixedDeposit.getAccountNumber() + " 1000"
        );

        System.out.println(
            "\n=== TRANSACTION HISTORY ==="
        );

        for (String transaction :
                store.getTransactionHistory()) {

            System.out.println(transaction);
        }

        // STEP 8: CONCURRENT HASHMAP

        System.out.println(
            "\n=== CONCURRENT HASHMAP TEST ==="
        );

        TransactionProcessor processor =
                new TransactionProcessor();

        processor.addAccount(savings);
        processor.addAccount(current);
        processor.addAccount(fixedDeposit);

        System.out.println(
            "Thread-safe accounts stored: "
            + processor.getAllAccounts().size()
        );

        processor.stop();

        // STEP 9: COMPLETION MESSAGE

        System.out.println(
            "\n=== PRACTICAL 12 COMPLETED SUCCESSFULLY ==="
        );
    }

    // ==========================================
    // MINIBANK MENU
    // ==========================================

    private static void showMenu() {

        try (Scanner scanner = new Scanner(System.in)) {

            boolean running = true;

            while (running) {

                System.out.println(
                    "\n=== MINIBANK MENU ==="
                );

                System.out.println("1. Open Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. Exit");

                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {

                    System.out.println(
                        "Invalid input. Enter a number."
                    );

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