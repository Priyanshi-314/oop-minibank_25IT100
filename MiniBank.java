import java.util.Scanner;

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
            "\n=== PRACTICAL 5: INHERITANCE AND POLYMORPHISM ==="
        );

        // Create different types of accounts
        Account[] accounts = new Account[3];

        accounts[0] = new SavingsAccount(
            "Priyanshi", 5000, 1000
        );

        accounts[1] = new CurrentAccount(
            "Rahul", 3000, 2000
        );

        accounts[2] = new FixedDepositAccount(
            "Anjali", 10000
        );

        // Runtime polymorphism
        System.out.println("\n=== ACCOUNT DETAILS ===");

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
                "Monthly Interest: Rs. %.2f%n",
                account.monthlyInterest()
            );

            // Pattern matching with instanceof
            if (account instanceof SavingsAccount savings) {

                System.out.println(
                    "Minimum Balance: Rs. "
                    + savings.getMinBalance()
                );
            }

            if (account instanceof CurrentAccount current) {

                System.out.println(
                    "Overdraft Limit: Rs. "
                    + current.getOverdraftLimit()
                );
            }
        }

        // Withdrawal demonstration
        System.out.println(
            "\n=== WITHDRAWAL RULE DEMONSTRATION ==="
        );

        System.out.println(
            "\nAccount Holder: " + accounts[0].getOwnerName()
        );

        System.out.println(
            "Account Number: " + accounts[0].getAccountNumber()
        );

        System.out.println("Attempting to withdraw Rs. 4500");

        accounts[0].withdraw(4500);

        System.out.println(
            "Final Balance: Rs. " + accounts[0].getBalance()
        );

        System.out.println(
            "\nAccount Holder: " + accounts[1].getOwnerName()
        );

        System.out.println(
            "Account Number: " + accounts[1].getAccountNumber()
        );

        System.out.println("Attempting to withdraw Rs. 4000");

        accounts[1].withdraw(4000);

        System.out.println(
            "Final Balance: Rs. " + accounts[1].getBalance()
        );

        System.out.println(
            "\nAccount Holder: " + accounts[2].getOwnerName()
        );

        System.out.println(
            "Account Number: " + accounts[2].getAccountNumber()
        );

        System.out.println("Attempting to withdraw Rs. 1000");

        accounts[2].withdraw(1000);

        System.out.println(
            "Final Balance: Rs. " + accounts[2].getBalance()
        );

        // Existing menu
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