import java.util.Scanner;

// Practical 1: Record
record BankInfo(String name, String branch) {}

// Practical 1: Enum
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

        // ---------------------------------
        // PRACTICAL 2: CUSTOMER AND ACCOUNT
        // ---------------------------------

        Customer.Address address1 =
            new Customer.Address(
                "Silvassa",
                "Dadra and Nagar Haveli",
                "396230"
            );

        Customer customer1 = new Customer(
            "Priyanshi",
            "priyanshi@example.com",
            "9876543210",
            address1
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

        // Array of Account objects
        Account[] accounts = new Account[3];

        accounts[0] = new Account("Priyanshi", 5000);
        accounts[1] = new Account("Rahul", 3000);
        accounts[2] = new Account("Anjali");

        System.out.println("\n=== INITIAL ACCOUNTS ===");

        for (Account account : accounts) {
            account.displayAccount();
        }

        // ---------------------------------
        // PRACTICAL 2: TRANSACTIONS
        // ---------------------------------

        System.out.println(
            "\n=== TRANSACTION DEMONSTRATION ==="
        );
        System.out.println("Account Holder : " + accounts[0].getOwnerName());
        System.out.println("Account Number : " + accounts[0].getAccountNumber());

        System.out.println(
            "Initial Balance: Rs. "
            + accounts[0].getBalance()
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

        // ---------------------------------
        // PRACTICAL 3: OBJECT METHODS
        // ---------------------------------

        System.out.println(
            "\n=== PRACTICAL 3: OBJECT METHODS ==="
        );

        // 1. toString()
        System.out.println("\n1. toString()");

        System.out.println(accounts[0]);

        // 2. equals()
        System.out.println("\n2. equals()");

        System.out.println(
            "Account 1 equals Account 2: "
            + accounts[0].equals(accounts[1])
        );

        System.out.println(
            "Account 1 equals itself: "
            + accounts[0].equals(accounts[0])
        );

        // 3. hashCode()
        System.out.println("\n3. hashCode()");

        System.out.println(
            "Account 1 hashCode: "
            + accounts[0].hashCode()
        );

        // 4. clone()
        System.out.println("\n4. clone()");

        Account clonedAccount = accounts[0].clone();

        System.out.println(
            "Original Account: " + accounts[0]
        );

        System.out.println(
            "Cloned Account  : " + clonedAccount
        );

        System.out.println(
            "Original equals Clone: "
            + accounts[0].equals(clonedAccount)
        );

        System.out.println(
            "Same object in memory: "
            + (accounts[0] == clonedAccount)
        );

        System.out.println(
            "Same hashCode: "
            + (accounts[0].hashCode()
                == clonedAccount.hashCode())
        );

        // 5. instanceof
        System.out.println("\n5. instanceof");

        System.out.println(
            "Is clonedAccount an Account? "
            + (clonedAccount instanceof Account)
        );

        Object obj = customer1;

        System.out.println(
            "Is obj a Customer? "
            + (obj instanceof Customer)
        );

        System.out.println(
            "Is obj an Account? "
            + (obj instanceof Account)
        );

        // 6. Nested Address class
        System.out.println("\n6. Nested Address Class");

        System.out.println(
            "Customer Name: " + customer1.getName()
        );

        System.out.println(
            "Customer Address: " + customer1.getAddress()
        );

        // ---------------------------------
        // PRACTICAL 1: MENU SYSTEM
        // ---------------------------------

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