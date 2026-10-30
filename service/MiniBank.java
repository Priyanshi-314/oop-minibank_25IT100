
package service;

import exception.BankException;
import model.Account;
import java.util.Scanner;

public class MiniBank {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final BankService BANK = new BankService();

    public static void main(String[] args) {
        System.out.println("\n========== WELCOME TO MINIBANK ==========");
        try {
            System.out.println("Loaded " + BANK.load() + " saved account(s).");
        } catch (Exception e) {
            System.out.println("Could not load saved data: " + e.getMessage());
        }

        while (true) {
            menu();
            int choice = (int) number("Enter your choice: ");

            if (choice == 0) {
                try {
                    BANK.save();
                    System.out.println("Accounts saved successfully.");
                } catch (Exception e) {
                    System.out.println("Save failed: " + e.getMessage());
                }
                System.out.println("Thank you for using MiniBank!");
                break;
            }

            try {
                handle(choice);
            } catch (Exception e) {
                System.out.println("TRANSACTION FAILED: " + e.getMessage());
            }
        }
    }

    private static void menu() {
        System.out.println("\n========== MINIBANK MENU ==========");
        System.out.println(" 1. Open Account");
        System.out.println(" 2. Deposit Money");
        System.out.println(" 3. Withdraw Money");
        System.out.println(" 4. Transfer Money");
        System.out.println(" 5. Check Balance");
        System.out.println(" 6. View Account Details");
        System.out.println(" 7. View All Accounts");
        System.out.println(" 8. Search Account");
        System.out.println(" 9. Sort Accounts by Balance");
        System.out.println("10. View Account Statement");
        System.out.println("11. Calculate Monthly Interest");
        System.out.println("12. Run Concurrent Transaction Demo");
        System.out.println("13. Save Account Data");
        System.out.println("14. Load Account Data");
        System.out.println("15. Generate Transaction Report");
        System.out.println("16. View Session Transaction History");
        System.out.println("17. Project Features");
        System.out.println(" 0. Exit");
    }

    private static String ask(String prompt) {
        System.out.print(prompt);
        return INPUT.nextLine().trim();
    }

    private static long number(String prompt) {
        while (true) {
            try {
                return Long.parseLong(ask(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static void display(Account a) {
        System.out.printf("%s | %-18s | %-19s | Rs. %d%n",
                a.getAccountNumber(),
                a.getOwnerName(),
                a.getClass().getSimpleName(),
                a.getBalance());
    }

    private static void handle(int choice) throws Exception {
        switch (choice) {
            case 1 -> {
                System.out.println("1. Savings | 2. Current | 3. Fixed Deposit");
                int type = (int) number("Type: ");
                String name = ask("Customer name: ");
                long opening = number("Opening balance: ");

                long limit = type == 1
                        ? number("Minimum balance: ")
                        : type == 2
                        ? number("Overdraft limit: ") : 0;

                Account a = BANK.openAccount(type, name, opening, limit);
                System.out.println("ACCOUNT CREATED SUCCESSFULLY");
                display(a);
            }

            case 2 -> {
                String id = ask("Account number: ");
                long before = BANK.getAccount(id).getBalance();
                long amount = number("Deposit amount: ");
                BANK.deposit(id, amount);

                System.out.printf(
                        "Previous: Rs. %d | Deposited: Rs. %d | New: Rs. %d%n",
                        before, amount, BANK.getAccount(id).getBalance());
            }

            case 3 -> {
                String id = ask("Account number: ");
                long before = BANK.getAccount(id).getBalance();
                long amount = number("Withdrawal amount: ");
                BANK.withdraw(id, amount);

                System.out.printf(
                        "Previous: Rs. %d | Withdrawn: Rs. %d | New: Rs. %d%n",
                        before, amount, BANK.getAccount(id).getBalance());
            }

            case 4 -> {
                String from = ask("Sender account: ");
                String to = ask("Receiver account: ");
                long amount = number("Transfer amount: ");
                BANK.transfer(from, to, amount);

                System.out.println("Sender balance: Rs. "
                        + BANK.getAccount(from).getBalance());
                System.out.println("Receiver balance: Rs. "
                        + BANK.getAccount(to).getBalance());
            }

            case 5 -> {
                Account a = BANK.getAccount(ask("Account number: "));
                System.out.println("Balance: Rs. " + a.getBalance());
            }

            case 6, 8 ->
                display(BANK.getAccount(ask("Account number: ")));

            case 7 -> {
                System.out.println("ALL ACCOUNTS");
                for (Account a : BANK.listAccounts()) {
                    display(a);
                }
            }

            case 9 -> {
                System.out.println("ACCOUNTS SORTED BY BALANCE (HIGH TO LOW)");
                for (Account a : BANK.sortedByBalance()) {
                    display(a);
                }
            }

            case 10 ->
                System.out.println(BANK.statement(ask("Account number: ")));

            case 11 -> {
                Account a = BANK.getAccount(ask("Account number: "));
                System.out.printf(
                        "Annual interest rate: %.2f%% | Monthly interest: Rs. %.2f%n",
                        a.interestRate(), a.monthlyInterest());
            }

            case 12 -> BANK.concurrentDemo();

            case 13 -> {
                BANK.save();
                System.out.println("Account data saved.");
            }

            case 14 ->
                System.out.println("Loaded " + BANK.load() + " saved account(s).");

            case 15 -> BANK.report();

            case 16 -> {
                System.out.println("SESSION TRANSACTION HISTORY");
                for (String line : BANK.history()) {
                    System.out.println(line);
                }
            }

            case 17 ->
                System.out.println("OOP, inheritance, interfaces, exceptions, "
                        + "reflection, generics, collections, threads, "
                        + "serialization, logs and reports.");

            default -> System.out.println("Invalid menu choice.");
        }
    }
}
