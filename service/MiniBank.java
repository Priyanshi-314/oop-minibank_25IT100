package service;

import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;

import persistence.StatePersister;
import persistence.TransactionLog;
import persistence.ReportGenerator;

// Bank information record
record BankInfo(String name, String branch) {}

// Menu options
enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

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

        // PRACTICAL 11 DEMONSTRATION
        practical11Demo();

        // EXISTING MINIBANK MENU
        showMenu();
    }

    // ==========================================
    // PRACTICAL 11: FILE HANDLING
    // ==========================================

    private static void practical11Demo() {

        System.out.println(
            "\n=== PRACTICAL 11: FILE HANDLING AND SERIALIZATION ==="
        );

        try {

            // STEP 1: CREATE ACCOUNTS

            Account[] accounts = {

                new SavingsAccount(
                    "Priyanshi",
                    5000,
                    1000
                ),

                new CurrentAccount(
                    "Rahul",
                    3000,
                    2000
                ),

                new FixedDepositAccount(
                    "Anjali",
                    10000
                )
            };

            // STEP 2: DEFINE FILE PATHS

            Path dataFile = Path.of(
                "data",
                "accounts.dat"
            );

            Path logFile = Path.of(
                "logs",
                "transactions.log"
            );

            Path reportFile = Path.of(
                "reports",
                "report.txt"
            );

            // STEP 3: SAVE ACCOUNTS

            System.out.println(
                "\n=== SAVING ACCOUNT DATA ==="
            );

            StatePersister.save(
                accounts,
                dataFile
            );

            System.out.println(
                "Accounts saved successfully."
            );

            System.out.println(
                "File: " + dataFile
            );

            // STEP 4: LOAD ACCOUNTS

            System.out.println(
                "\n=== LOADING ACCOUNT DATA ==="
            );

            Account[] loadedAccounts =
                    StatePersister.load(dataFile);

            System.out.println(
                "Accounts loaded successfully."
            );

            // STEP 5: DISPLAY LOADED ACCOUNTS

            System.out.println(
                "\n=== LOADED ACCOUNT DETAILS ==="
            );

            for (Account account : loadedAccounts) {

                System.out.println(
                    "------------------------------"
                );

                System.out.println(
                    "Account Number : "
                    + account.getAccountNumber()
                );

                System.out.println(
                    "Account Holder : "
                    + account.getOwnerName()
                );

                System.out.println(
                    "Account Type   : "
                    + account.getClass().getSimpleName()
                );

                System.out.println(
                    "Balance        : Rs. "
                    + account.getBalance()
                );
            }

            // STEP 6: CREATE TRANSACTION LOG

            System.out.println(
                "\n=== TRANSACTION LOGGING ==="
            );

            // Reset the demonstration log on each run.
            Files.deleteIfExists(logFile);

            TransactionLog.append(
                logFile,
                "DEPOSIT AC0001 500"
            );

            TransactionLog.append(
                logFile,
                "WITHDRAW AC0002 200"
            );

            TransactionLog.append(
                logFile,
                "DEPOSIT AC0003 1000"
            );

            System.out.println(
                "Transactions logged successfully."
            );

            System.out.println(
                "Log File: " + logFile
            );

            // STEP 7: GENERATE REPORT

            System.out.println(
                "\n=== GENERATING BANK REPORT ==="
            );

            ReportGenerator.generate(
                Path.of("logs"),
                reportFile
            );

            System.out.println(
                "Report generated successfully."
            );

            System.out.println(
                "Report File: " + reportFile
            );

            // STEP 8: SUCCESS MESSAGE

            System.out.println(
                "\n=== PRACTICAL 11 COMPLETED SUCCESSFULLY ==="
            );

        } catch (Exception e) {

            System.out.println(
                "File Handling Error: "
                + e.getMessage()
            );

            e.printStackTrace();
        }
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

                System.out.println(
                    "1. Open Account"
                );

                System.out.println(
                    "2. Deposit"
                );

                System.out.println(
                    "3. Withdraw"
                );

                System.out.println(
                    "4. Transfer"
                );

                System.out.println(
                    "5. Exit"
                );

                System.out.print(
                    "Enter your choice: "
                );

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