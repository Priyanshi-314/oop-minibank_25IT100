package service;

import java.util.Scanner;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;

import repository.Repository;


// ==========================================
// BANK INFORMATION RECORD
// ==========================================

record BankInfo(String name, String branch) {}

// ==========================================
// MENU OPTIONS
// ==========================================

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

// ==========================================
// MAIN MINIBANK CLASS
// ==========================================

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

        // RUN PRACTICAL 13
        practical13Demo();

        // DISPLAY MINIBANK MENU
        showMenu();
    }

    // ==========================================
    // PRACTICAL 13: GENERICS AND REPOSITORY
    // ==========================================

    private static void practical13Demo() {

        System.out.println(
            "\n=== PRACTICAL 13: GENERICS AND REPOSITORY ==="
        );

        // STEP 1: CREATE GENERIC REPOSITORY

        Repository<Account> repository = new Repository<>();

        // STEP 2: CREATE THREE ACCOUNTS

        Account savings = new SavingsAccount(
            "Priyanshi",
            5000,
            1000
        );

        Account current = new CurrentAccount(
            "Rahul",
            3000,
            2000
        );

        Account fixedDeposit = new FixedDepositAccount(
            "Anjali",
            10000
        );

        // STEP 3: SAVE MULTIPLE ACCOUNTS

        repository.saveAll(
            savings,
            current,
            fixedDeposit
        );

        System.out.println(
            "\n=== ACCOUNTS SAVED SUCCESSFULLY ==="
        );

        System.out.println(
            "Total Accounts: " + repository.count()
        );

        // STEP 4: SEARCH ACCOUNT BY ID

        System.out.println(
            "\n=== SEARCH ACCOUNT BY ID ==="
        );

        String searchId = current.getAccountNumber();

        Account found = repository.findById(searchId);

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

        // STEP 5: DISPLAY ALL ACCOUNTS

        System.out.println(
            "\n=== ALL REPOSITORY ACCOUNTS ==="
        );

        for (Account account : repository.findAll()) {

            System.out.println(
                account.getAccountNumber()
                + " | "
                + account.getOwnerName()
                + " | Rs. "
                + account.getBalance()
            );
        }

        // STEP 6: BOUNDED GENERIC METHOD

        System.out.println(
            "\n=== GENERIC AVERAGE CALCULATION ==="
        );

        Integer[] integerValues = {
            10, 20, 30, 40, 50
        };

        Double[] doubleValues = {
            10.5, 20.5, 30.5
        };

        System.out.println(
            "Integer Average: "
            + Repository.average(integerValues)
        );

        System.out.println(
            "Double Average : "
            + Repository.average(doubleValues)
        );

        // STEP 7: DELETE ACCOUNT

        System.out.println(
            "\n=== DELETE ACCOUNT ==="
        );

        boolean deleted = repository.delete(
            fixedDeposit.getAccountNumber()
        );

        System.out.println(
            "Account Deleted: " + deleted
        );

        System.out.println(
            "Remaining Accounts: " + repository.count()
        );

        // STEP 8: COMPLETION MESSAGE

        System.out.println(
            "\n=== PRACTICAL 13 COMPLETED SUCCESSFULLY ==="
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

                    System.out.println(
                        "Invalid choice."
                    );

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