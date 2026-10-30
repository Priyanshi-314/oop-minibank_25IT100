package service;

import java.util.ArrayList;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import model.Account;

public class TransactionProcessor {

    private final ExecutorService executor;

    private final ConcurrentHashMap<String, Account> accounts;

    public TransactionProcessor() {

        executor = Executors.newFixedThreadPool(4);

        accounts = new ConcurrentHashMap<>();
    }

    // Add account to thread-safe map
    public void addAccount(Account account) {

        accounts.put(
            account.getAccountNumber(),
            account
        );
    }

    // Find account
    public Account getAccount(String accountNumber) {

        return accounts.get(accountNumber);
    }

    // Return all accounts
    public ArrayList<Account> getAllAccounts() {

        return new ArrayList<>(accounts.values());
    }

    // Submit transaction task
    public void submit(Runnable task) {

        executor.execute(task);
    }

    // Stop thread pool
    public void stop() {

        executor.shutdown();

        try {

            if (!executor.awaitTermination(
                    60,
                    TimeUnit.SECONDS)) {

                executor.shutdownNow();
            }

        } catch (InterruptedException e) {

            executor.shutdownNow();

            Thread.currentThread().interrupt();
        }
    }
}