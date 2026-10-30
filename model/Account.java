package model;

import java.util.Objects;
import model.annotation.Id;
import model.annotation.Positive;
import model.annotation.MaxLength;

public abstract class Account
        implements Cloneable, Transactable, InterestBearing {

 @Id
private final String accountNumber;

@MaxLength(30)
private String ownerName;

@Positive(message = "must be > 0")
private long balance;

private boolean active;

    private static long accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

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

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    public double monthlyInterest() {
        return balance * interestRate() / 1200.0;
    }

    @Override
    public void deposit(long amount) {

        if (!active) {
            System.out.println("Account is inactive.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }

        balance = Math.addExact(balance, amount);

        System.out.println("Deposit successful: Rs. " + amount);
    }

    @Override
    public boolean withdraw(long amount) {

        if (!active) {
            System.out.println("Account is inactive.");
            return false;
        }

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }

        if (!canWithdraw(amount)) {
            System.out.println(
                "Withdrawal rejected: Account withdrawal rule violated."
            );
            return false;
        }

        balance = Math.subtractExact(balance, amount);

        System.out.println("Withdrawal successful: Rs. " + amount);
        return true;
    }

    public void displayAccount() {

        System.out.println("------------------------------");
        System.out.println("Account No : " + accountNumber);
        System.out.println("Owner      : " + ownerName);
        System.out.println("Balance    : Rs. " + balance);
        System.out.println("Active     : " + active);
    }

    @Override
    public String toString() {

        return "Account{accountNumber='" + accountNumber
                + "', ownerName='" + ownerName
                + "', balance=" + balance
                + ", active=" + active + "}";
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Account other)) {
            return false;
        }

        return Objects.equals(
            this.accountNumber,
            other.accountNumber
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public Account clone() {

        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}