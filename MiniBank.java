import java.util.Scanner;

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
}

public class MiniBank {

    public static void main(String[] args) {

        BankInfo bank = new BankInfo("MiniBank", "Main Branch");

        System.out.println("=== " + bank.name() + " | " 
                           + bank.branch() + " ===");

        try (Scanner scanner = new Scanner(System.in)) {

            boolean running = true;

            while (running) {

                System.out.println("\n1. Open Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. Exit");

                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println(
                        "Invalid input. Please enter a number from 1 to 5."
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
                        "Invalid choice. Please enter 1 to 5."
                    );
                    continue;
                }

                String message = switch (option) {

                    case OPEN_ACCOUNT ->
                        "Open Account - to be implemented in a later lab.";

                    case DEPOSIT ->
                        "Deposit - to be implemented in a later lab.";

                    case WITHDRAW ->
                        "Withdraw - to be implemented in a later lab.";

                    case TRANSFER ->
                        "Transfer - to be implemented in a later lab.";

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