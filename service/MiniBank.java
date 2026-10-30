package service;

import java.util.Scanner;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.Premium;

// Static import
import static util.Validator.isValidMobile;

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
            "\n=== PRACTICAL 6: INTERFACES AND PACKAGES ==="
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

        Account[] accounts = {
            savings,
            current,
            fixedDeposit
        };

        // 1. DEFAULT METHODS

        System.out.println(
            "\n1. INTERFACE AND DEFAULT METHOD"
        );

        for (Account account : accounts) {

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

            System.out.println(
                "Interest Rate  : " + account.interestRate() + "%"
            );

            System.out.printf(
                "Yearly Interest: Rs. %.2f%n",
                account.yearlyInterest()
            );
        }

        // 2. ANONYMOUS CLASS

        System.out.println(
            "\n2. WITHDRAW RULE USING ANONYMOUS CLASS"
        );

        WithdrawRule anonymousRule = new WithdrawRule() {

            @Override
            public boolean allow(Account account, long amount) {

                return account.isActive()
                        && account.canWithdraw(amount);
            }
        };

        System.out.println(
            "Account Holder : " + savings.getOwnerName()
        );

        System.out.println(
            "Account Number : " + savings.getAccountNumber()
        );

        System.out.println("Requested Withdrawal: Rs. 3000");

        System.out.println(
            "Withdrawal Allowed: "
            + anonymousRule.allow(savings, 3000)
        );

        // 3. LAMBDA EXPRESSION

        System.out.println(
            "\n3. WITHDRAW RULE USING LAMBDA"
        );

        WithdrawRule lambdaRule =
                (account, amount) ->
                        account.isActive()
                        && account.canWithdraw(amount);

        System.out.println(
            "Account Holder : " + savings.getOwnerName()
        );

        System.out.println(
            "Account Number : " + savings.getAccountNumber()
        );

        System.out.println("Requested Withdrawal: Rs. 4500");

        System.out.println(
            "Withdrawal Allowed: "
            + lambdaRule.allow(savings, 4500)
        );

        // 4. MARKER INTERFACE

        System.out.println(
            "\n4. PREMIUM MARKER INTERFACE"
        );

        for (Account account : accounts) {

            System.out.println(
                account.getOwnerName()
                + " - Premium Account: "
                + (account instanceof Premium)
            );
        }

        // 5. STATIC IMPORT

        System.out.println(
            "\n5. STATIC IMPORT DEMONSTRATION"
        );

        System.out.println("Mobile: 9876543210");

        System.out.println(
            "Valid Mobile: "
            + isValidMobile("9876543210")
        );

        System.out.println("Mobile: 12345");

        System.out.println(
            "Valid Mobile: "
            + isValidMobile("12345")
        );

        // 6. TRANSACTABLE INTERFACE

        System.out.println(
            "\n6. TRANSACTABLE INTERFACE"
        );

        System.out.println(
            "Account Holder : " + savings.getOwnerName()
        );

        System.out.println(
            "Account Number : " + savings.getAccountNumber()
        );

        System.out.println(
            "Initial Balance: Rs. " + savings.getBalance()
        );

        savings.deposit(1000);

        System.out.println(
            "Final Balance: Rs. " + savings.getBalance()
        );

        // 7. MINIBANK MENU

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