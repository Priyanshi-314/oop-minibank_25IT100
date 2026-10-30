import java.util.Scanner;

// Record introduced in Practical 1
record BankInfo(String name, String branch) {}

// Enum introduced in Practical 1
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

        // PRACTICAL 2: Create customers
        Customer customer1 = new Customer(
            "Priyanshi",
            "priyanshi@example.com",
            "9876543210"
        );

        Customer customer2 = new Customer(
            "Rahul",
            "rahul@example.com",
            "9876501234"
        );

        System.out.println("\n=== CUSTOMER DETAILS ===");

        customer1.displayCustomer();

        System.out.println();

        customer2.displayCustomer();

        // PRACTICAL 2: Array of Account objects
        Account[] accounts = new Account[3];

        accounts[0] = new Account("Priyanshi", 5000);
        accounts[1] = new Account("Rahul", 3000);
        accounts[2] = new Account("Anjali");

        System.out.println("\n=== INITIAL ACCOUNTS ===");

        for (Account account : accounts) {
            account.displayAccount();
        }

        // Deposit and withdrawal demonstration
        System.out.println("\n=== TRANSACTION DEMONSTRATION ===");

        System.out.println(
            "Initial Balance: Rs. " + accounts[0].getBalance()
        );

        accounts[0].deposit(2000);

        System.out.println(
            "Balance After Deposit: Rs. "
            + accounts[0].getBalance()
        );

        accounts[0].withdraw(3000);

        System.out.println(
            "Balance After Withdrawal: Rs. "
            + accounts[0].getBalance()
        );

        boolean result = accounts[0].withdraw(10000);

        System.out.println(
            "Withdrawal Status: " + result
        );

        System.out.println(
            "Final Balance: Rs. "
            + accounts[0].getBalance()
        );

        // Menu from Practical 1
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
                    System.out.println(
                        "Invalid input. Enter a number from 1 to 5."
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