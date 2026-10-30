public class CommandParser {

    public static Command parse(String line) {

        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException(
                "Command cannot be empty."
            );
        }

        // Remove extra spaces and split the command
        String[] parts = line.trim().split("\\s+");

        // A command must contain exactly 3 parts
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                "Invalid command. Use: TYPE ACCOUNT_NUMBER AMOUNT"
            );
        }

        // Convert transaction name to enum
        TransactionType type;

        try {
            type = TransactionType.valueOf(
                parts[0].toUpperCase()
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid transaction type: " + parts[0]
            );
        }

        String accountNumber = parts[1];

        // Check account number format
        if (!accountNumber.matches("^AC[0-9]{4}$")) {
            throw new IllegalArgumentException(
                "Invalid account number format."
            );
        }

        // Check amount
        if (!Validator.isValidAmount(parts[2])) {
            throw new IllegalArgumentException(
                "Amount must be a positive whole number."
            );
        }

        long amount = Long.parseLong(parts[2]);

        return new Command(
            type,
            accountNumber,
            amount
        );
    }
}