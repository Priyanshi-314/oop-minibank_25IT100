package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import model.Account;

public class AccountStore {

    private final HashMap<String, Account> accounts =
            new HashMap<>();

    private final ArrayList<String> transactionHistory =
            new ArrayList<>();

    // Add an account
    public void add(Account account) {

        accounts.put(
            account.getAccountNumber(),
            account
        );
    }

    // Search account using account number
    public Account get(String accountNumber) {

        return accounts.get(accountNumber);
    }

    // Return all accounts
    public ArrayList<Account> getAll() {

        return new ArrayList<>(accounts.values());
    }

    // Sort accounts by balance (highest first)
    public ArrayList<Account> sortedByBalance() {

        ArrayList<Account> sortedAccounts = getAll();

        sortedAccounts.sort(
            Comparator.comparingLong(Account::getBalance)
                      .reversed()
        );

        return sortedAccounts;
    }

    // Add transaction history
    public void addTransaction(String transaction) {

        transactionHistory.add(transaction);
    }

    // Display transaction history
    public ArrayList<String> getTransactionHistory() {

        return new ArrayList<>(transactionHistory);
    }

    // Count total accounts
    public int getAccountCount() {

        return accounts.size();
    }
}

