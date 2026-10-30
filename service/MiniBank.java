package service;

import java.util.Scanner;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;

import exception.BankException;
import exception.InvalidAmountException;
import exception.InsufficientFundsException;

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
            "\n=== PRACTICAL 8: EXCEPTION HANDLING ==="
        );

        Account savings = new SavingsAccount(
            "Priyanshi", 5000, 1000
        );

        Account current = new CurrentAccount(
            "Rahul", 3000, 2000
        );

        Account fixedDeposit = new FixedDepositAccount(
            "Anjali", 10000
        );

        System.out.println("\n=== INITIAL ACCOUNT DETAILS ===");

        printAccount(savings);
        printAccount(current);
        printAccount(fixedDeposit);

        // 1. Invalid deposit
        System.out.println(
            "\n1. INVALID DEPOSIT TEST"
        );

        try {

            savings.deposit(-500);

        } catch (InvalidAmountException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
        }

        // 2. Invalid withdrawal
        System.out.println(
            "\n2. INVALID WITHDRAWAL TEST"
        );

        try {

            savings.withdraw(0);

        } catch (BankException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
        }

        // 3. Insufficient funds
        System.out.println(
            "\n3. INSUFFICIENT FUNDS TEST"
        );

        try {

            System.out.println(
                "Account Holder: " + savings.getOwnerName()
            );

            System.out.println(
                "Account Number: " + savings.getAccountNumber()
            );

            System.out.println(
                "Attempting Withdrawal: Rs. 6000"
            );

            savings.withdraw(6000);

        } catch (InsufficientFundsException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );

            System.out.println(
                "Shortfall: Rs. " + e.getShortfall()
            );

        } catch (InvalidAmountException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );

        } finally {

            System.out.println(
                "Withdrawal attempt completed."
            );
        }

        // 4. Successful deposit
        System.out.println(
            "\n4. SUCCESSFUL DEPOSIT TEST"
        );

        try {

            savings.deposit(2000);

        } catch (InvalidAmountException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
        }

        // 5. Successful transfer
        System.out.println(
            "\n5. SUCCESSFUL TRANSFER TEST"
        );

        try {

            savings.transfer(current, 1500);

        } catch (BankException e) {

            System.out.println(
                "Error handled: " + e.getMessage()
            );
        }

        // 6. Failed transfer
        System.out.println(
            "\n6. FAILED TRANSFER TEST"
        );

        try {

            savings.transfer(current, 10000);

        } catch (BankException e) {

            System.out.println(
                "Error handled: " + e.getMessage()
            );
        }

        // 7. Fixed deposit withdrawal
        System.out.println(
            "\n7. FIXED DEPOSIT WITHDRAWAL TEST"
        );

        try {

            fixedDeposit.withdraw(1000);

        } catch (BankException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
        }

        // 8. Try-with-resources
        System.out.println(
            "\n8. TRY-WITH-RESOURCES TEST"
        );

        try (BankResource resource = new BankResource()) {

            resource.process();

        } catch (Exception e) {

            System.out.println(
                "Resource Error: " + e.getMessage()
            );
        }

        // Final account balances
        System.out.println(
            "\n=== FINAL ACCOUNT DETAILS ==="
        );

        printAccount(savings);
        printAccount(current);
        printAccount(fixedDeposit);

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