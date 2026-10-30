public class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static long accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    // Parameterized constructor
    public Account(String ownerName, long openingBalance) {

        if (openingBalance < 0) {
            throw new IllegalArgumentException(
                "Opening balance cannot be negative."
            );
        }

        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    // Overloaded constructor
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void deposit(long amount) {

        if (!active) {
            System.out.println("Account is inactive.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }

        balance += amount;

        System.out.println(
            "Deposit successful: Rs. " + amount
        );
    }

    public boolean withdraw(long amount) {

        if (!active) {
            System.out.println("Account is inactive.");
            return false;
        }

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }

        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return false;
        }

        balance -= amount;

        System.out.println(
            "Withdrawal successful: Rs. " + amount
        );

        return true;
    }

    public void displayAccount() {

        System.out.println("------------------------------");
        System.out.println("Account No : " + accountNumber);
        System.out.println("Owner      : " + ownerName);
        System.out.println("Balance    : Rs. " + balance);
        System.out.println("Active     : " + active);
    }
}