import java.util.Scanner;

// PRACTICAL 1: Record
record BankInfo(String name, String branch) {}

// PRACTICAL 1: Enum
enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

public class MiniBank {

    public static void main(String[] args) {

        // ====================================
        // PRACTICAL 1: BANK INFORMATION
        // ====================================

        BankInfo bank = new BankInfo(
            "MiniBank",
            "Main Branch"
        );

        System.out.println(
            "=== " + bank.name()
            + " | " + bank.branch() + " ==="
        );

        // ====================================
        // PRACTICAL 2: CUSTOMER DETAILS
        // ====================================

        Customer.Address address =
                new Customer.Address(
                    "Silvassa",
                    "Dadra and Nagar Haveli",
                    "396230"
                );

        Customer customer1 = new Customer(
            "Priyanshi",
            "priyanshi@example.com",
            "9876543210",
            address
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

        // ====================================
        // PRACTICAL 2: ACCOUNT OBJECTS
        // ====================================

        Account[] accounts = new Account[3];

        accounts[0] = new Account("Priyanshi", 5000);
        accounts[1] = new Account("Rahul", 3000);
        accounts[2] = new Account("Anjali");

        System.out.println("\n=== INITIAL ACCOUNTS ===");

        for (Account account : accounts) {
            account.displayAccount();
        }

        // ====================================
        // PRACTICAL 2: TRANSACTIONS
        // ====================================

        System.out.println(
            "\n=== TRANSACTION DEMONSTRATION ==="
        );

        System.out.println(
            "Account Holder : " + accounts[0].getOwnerName()
        );

        System.out.println(
            "Account Number : " + accounts[0].getAccountNumber()
        );

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
            "Final Balance: Rs. " + accounts[0].getBalance()
        );

        // ====================================
        // PRACTICAL 3: OBJECT METHODS
        // ====================================

        System.out.println(
            "\n=== PRACTICAL 3: OBJECT METHODS ==="
        );

        System.out.println("\n1. toString()");

        System.out.println(accounts[0]);

        System.out.println("\n2. equals()");

        System.out.println(
            "Account 1 equals Account 2: "
            + accounts[0].equals(accounts[1])
        );

        System.out.println(
            "Account 1 equals itself: "
            + accounts[0].equals(accounts[0])
        );

        System.out.println("\n3. hashCode()");

        System.out.println(
            "Account 1 hashCode: "
            + accounts[0].hashCode()
        );

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

        System.out.println("\n6. Nested Address Class");

        System.out.println(
            "Customer Name: " + customer1.getName()
        );

        System.out.println(
            "Customer Address: " + customer1.getAddress()
        );

        // ====================================
        // PRACTICAL 4: INPUT VALIDATION
        // ====================================

        System.out.println(
            "\n=== PRACTICAL 4: INPUT VALIDATION ==="
        );

        System.out.println("\n1. MOBILE VALIDATION");

        System.out.println(
            "9876543210 : "
            + Validator.isValidMobile("9876543210")
        );

        System.out.println(
            "12345      : "
            + Validator.isValidMobile("12345")
        );

        System.out.println("\n2. EMAIL VALIDATION");

        System.out.println(
            "abc@xyz.com : "
            + Validator.isValidEmail("abc@xyz.com")
        );

        System.out.println(
            "abcxyz.com  : "
            + Validator.isValidEmail("abcxyz.com")
        );

        System.out.println("\n3. PAN VALIDATION");

        System.out.println(
            "ABCDE1234F : "
            + Validator.isValidPan("ABCDE1234F")
        );

        System.out.println(
            "ABC123     : "
            + Validator.isValidPan("ABC123")
        );

        System.out.println("\n4. IFSC VALIDATION");

        System.out.println(
            "SBIN0001234 : "
            + Validator.isValidIfsc("SBIN0001234")
        );

        System.out.println(
            "12345       : "
            + Validator.isValidIfsc("12345")
        );

        System.out.println("\n5. AMOUNT VALIDATION");

        System.out.println(
            "500 : " + Validator.isValidAmount("500")
        );

        System.out.println(
            "-50 : " + Validator.isValidAmount("-50")
        );

        // ====================================
        // PRACTICAL 4: COMMAND PARSING
        // ====================================

        System.out.println(
            "\n=== PRACTICAL 4: COMMAND PARSING ==="
        );

        String commandText = "DEPOSIT AC0001 500";

        System.out.println(
            "Input Command : " + commandText
        );

        try {

            Command command = CommandParser.parse(commandText);

            System.out.println(
                "Transaction Type : " + command.type()
            );

            System.out.println(
                "Account Number   : " + command.accountNumber()
            );

            System.out.println(
                "Amount           : Rs. " + command.amount()
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                "Command Error: " + e.getMessage()
            );
        }

        // Test an invalid command
        System.out.println("\nInvalid Command Test:");

        try {

            CommandParser.parse("DEPOSIT AC0001 -500");

        } catch (IllegalArgumentException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
        }

        // ====================================
        // PRACTICAL 4: ACCOUNT STATEMENT
        // ====================================

        System.out.println(
            "\n=== PRACTICAL 4: ACCOUNT STATEMENT ==="
        );

        System.out.println(
            StatementFormatter.buildStatement(accounts[0])
        );

        // ====================================
        // PRACTICAL 1: MENU SYSTEM
        // ====================================

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