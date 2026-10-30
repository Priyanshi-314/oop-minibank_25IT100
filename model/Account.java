package model;

import java.util.Objects;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import exception.AccountNotFoundException;

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

    // Deposit with exception handling
    @Override
    public void deposit(long amount)
            throws InvalidAmountException {

        if (amount <= 0) {

            throw new InvalidAmountException(
                "Deposit amount must be greater than zero."
            );
        }

        if (!active) {

            throw new IllegalStateException(
                "Account is inactive."
            );
        }

        balance = Math.addExact(balance, amount);

        System.out.println(
            "Deposit successful: Rs. " + amount
        );
    }

    // Withdrawal with exception handling
    @Override
    public boolean withdraw(long amount)
            throws InsufficientFundsException,
                   InvalidAmountException {

        if (amount <= 0) {

            throw new InvalidAmountException(
                "Withdrawal amount must be greater than zero."
            );
        }

        if (!active) {

            throw new IllegalStateException(
                "Account is inactive."
            );
        }

        if (!canWithdraw(amount)) {

            long availableAmount = getAvailableWithdrawalAmount();

            long shortfall = Math.max(
                0,
                amount - availableAmount
            );

            throw new InsufficientFundsException(
                "Insufficient available funds or withdrawal not permitted.",
                shortfall
            );
        }

        balance = Math.subtractExact(balance, amount);

        System.out.println(
            "Withdrawal successful: Rs. " + amount
        );

        return true;
    }

    // Account-specific withdrawal availability
    public long getAvailableWithdrawalAmount() {

        if (this instanceof SavingsAccount savings) {

            return Math.max(
                0,
                getBalance() - savings.getMinBalance()
            );
        }

        if (this instanceof CurrentAccount current) {

            return Math.max(
                0,
                getBalance() + current.getOverdraftLimit()
            );
        }

        if (this instanceof FixedDepositAccount) {
            return 0;
        }

        return Math.max(0, getBalance());
    }

    // Transfer between two accounts
    public void transfer(Account to, long amount)
            throws BankException {

        System.out.println("\nStarting transfer...");

        boolean completed = false;

        try {

            if (to == null) {

                throw new AccountNotFoundException(
                    "Destination account not found."
                );
            }

            if (to == this) {

                throw new BankException(
                    "Cannot transfer to the same account."
                );
            }

            if (amount <= 0) {

                throw new InvalidAmountException(
                    "Transfer amount must be greater than zero."
                );
            }

            if (!this.active || !to.active) {

                throw new BankException(
                    "Both accounts must be active."
                );
            }

            // Check the receiving balance before debiting.
            Math.addExact(to.balance, amount);

            // Check the withdrawal rule before debiting.
            if (!canWithdraw(amount)) {

                long shortfall = Math.max(
                    0,
                    amount - getAvailableWithdrawalAmount()
                );

                throw new InsufficientFundsException(
                    "Insufficient funds for transfer.",
                    shortfall
                );
            }

            // Transfer funds only after all checks succeed.
            this.balance = Math.subtractExact(
                this.balance,
                amount
            );

            to.balance = Math.addExact(
                to.balance,
                amount
            );

            completed = true;

            System.out.println(
                "Transfer successful: Rs. " + amount
            );

            System.out.println(
                "From: " + this.accountNumber
            );

            System.out.println(
                "To  : " + to.accountNumber
            );

        } catch (BankException e) {

            System.out.println(
                "Transfer Error: " + e.getMessage()
            );

            throw e;

        } catch (ArithmeticException e) {

            throw new BankException(
                "Transfer amount exceeds supported balance range.",
                e
            );

        } finally {

            System.out.println(
                "Transfer Status: "
                + (completed ? "COMPLETED" : "FAILED")
            );
        }
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