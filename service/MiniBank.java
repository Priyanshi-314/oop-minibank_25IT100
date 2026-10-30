package service;

import java.lang.reflect.Field;
import java.util.Scanner;

import model.Account;
import model.SavingsAccount;

import util.AnnotationValidator;

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
            "\n=== PRACTICAL 7: ANNOTATIONS AND REFLECTION ==="
        );

        // Create a valid savings account
        Account account = new SavingsAccount(
            "Priyanshi",
            5000,
            1000
        );

        System.out.println("\n=== ACCOUNT DETAILS ===");

        System.out.println(
            "Account Holder : " + account.getOwnerName()
        );

        System.out.println(
            "Account Number : " + account.getAccountNumber()
        );

        System.out.println(
            "Account Balance: Rs. " + account.getBalance()
        );

        // Test valid account
        System.out.println("\n=== VALID ACCOUNT TEST ===");

        String[] validErrors =
                AnnotationValidator.validate(account);

        if (validErrors.length == 0) {

            System.out.println(
                "Validation successful: No errors found."
            );

        } else {

            for (String error : validErrors) {
                System.out.println(error);
            }
        }

        // Test invalid balance using reflection
        System.out.println("\n=== INVALID ACCOUNT TEST ===");

        try {

            Field balanceField =
                    Account.class.getDeclaredField("balance");

            balanceField.setAccessible(true);

            // Invalid state used only for validation testing
            balanceField.setLong(account, -100);

            System.out.println(
                "Testing Balance: Rs. " + account.getBalance()
            );

            String[] errors =
                    AnnotationValidator.validate(account);

            for (String error : errors) {

                System.out.println(
                    "Validation Error: " + error
                );
            }

            // Restore the original balance
            balanceField.setLong(account, 5000);

        } catch (ReflectiveOperationException e) {

            System.out.println(
                "Reflection Error: " + e.getMessage()
            );
        }

        // Test maximum length
        System.out.println("\n=== MAX LENGTH TEST ===");

        try {

            Field nameField =
                    Account.class.getDeclaredField("ownerName");

            nameField.setAccessible(true);

            String originalName = account.getOwnerName();

            nameField.set(
                account,
                "PriyanshiABCDEFGHIJKLMNOPQRSTUVWXYZ"
            );

            String[] errors =
                    AnnotationValidator.validate(account);

            for (String error : errors) {

                System.out.println(
                    "Validation Error: " + error
                );
            }

            // Restore the original name
            nameField.set(account, originalName);

        } catch (ReflectiveOperationException e) {

            System.out.println(
                "Reflection Error: " + e.getMessage()
            );
        }

        System.out.println("\n=== FINAL ACCOUNT STATUS ===");

        System.out.println(
            "Account Holder : " + account.getOwnerName()
        );

        System.out.println(
            "Account Number : " + account.getAccountNumber()
        );

        System.out.println(
            "Account Balance: Rs. " + account.getBalance()
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