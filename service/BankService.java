package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidAmountException;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import persistence.ReportGenerator;
import persistence.StatePersister;
import persistence.TransactionLog;
import repository.Repository;
import util.Validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BankService {
    private final Repository<Account> repository = new Repository<>();
    private final ConcurrentHashMap<String, Account> concurrentAccounts = new ConcurrentHashMap<>();
    private final ArrayList<String> history = new ArrayList<>();
    private final Path stateFile = Path.of("data", "accounts.dat");
    private final Path logFile = Path.of("logs", "transactions.log");

    public synchronized Account openAccount(int type, String name, long opening, long limit) {
        if (name == null || name.isBlank() || name.length() > 30)
            throw new IllegalArgumentException("Name must contain 1 to 30 characters.");
        if (opening < 0 || limit < 0)
            throw new IllegalArgumentException("Balance and limit cannot be negative.");
        Account account;
        switch (type) {
            case 1 -> {
                if (opening < limit) throw new IllegalArgumentException("Savings opening balance must cover minimum balance.");
                account = new SavingsAccount(name.trim(), opening, limit);
            }
            case 2 -> account = new CurrentAccount(name.trim(), opening, limit);
            case 3 -> account = new FixedDepositAccount(name.trim(), opening);
            default -> throw new IllegalArgumentException("Unknown account type.");
        }
        repository.save(account);
        concurrentAccounts.put(account.getAccountNumber(), account);
        history.add("OPEN " + account.getAccountNumber() + " " + opening);
        return account;
    }

    public Account getAccount(String id) throws AccountNotFoundException {
        Account a = concurrentAccounts.get(id == null ? "" : id.trim().toUpperCase());
        if (a == null) throw new AccountNotFoundException("Account not found: " + id);
        return a;
    }

    private void validateAmount(long amount) throws InvalidAmountException {
        if (!Validator.isValidAmount(Long.toString(amount)))
            throw new InvalidAmountException("Amount must be a positive whole number.");
    }

    public synchronized void deposit(String id, long amount) throws BankException, IOException {
        validateAmount(amount);
        Account account = getAccount(id);
        account.deposit(amount);
        record("DEPOSIT " + account.getAccountNumber() + " " + amount);
    }

    public synchronized void withdraw(String id, long amount) throws BankException, IOException {
        validateAmount(amount);
        Account account = getAccount(id);
        account.withdraw(amount);
        record("WITHDRAW " + account.getAccountNumber() + " " + amount);
    }

    public synchronized void transfer(String from, String to, long amount) throws BankException, IOException {
        validateAmount(amount);
        Account sender = getAccount(from);
        Account receiver = getAccount(to);
        sender.transfer(receiver, amount);
        // A transfer moves funds between accounts; it is not a new deposit or withdrawal.
        record("TRANSFER " + sender.getAccountNumber() + " " + receiver.getAccountNumber() + " " + amount);
    }

    private void record(String line) throws IOException {
        history.add(line);
        TransactionLog.append(logFile, line);
    }

    public synchronized ArrayList<Account> listAccounts() {
        ArrayList<Account> accounts = repository.findAll();
        accounts.sort(Comparator.naturalOrder());
        return accounts;
    }

    public synchronized ArrayList<Account> sortedByBalance() {
        ArrayList<Account> accounts = repository.findAll();
        accounts.sort(Comparator.comparingLong(Account::getBalance).reversed());
        return accounts;
    }

    public synchronized List<String> history() { return new ArrayList<>(history); }

    public synchronized String statement(String id) throws BankException {
        Account account = getAccount(id);
        StringBuilder text = new StringBuilder(StatementFormatter.buildStatement(account));
        text.append("RECENT TRANSACTIONS:\n");
        for (String line : history) if (line.contains(account.getAccountNumber())) text.append(line).append('\n');
        return text.toString();
    }

    public synchronized void save() throws IOException {
        StatePersister.save(repository.findAll().toArray(new Account[0]), stateFile);
    }

    public synchronized int load() throws IOException, ClassNotFoundException {
        if (!Files.exists(stateFile)) return 0;
        Account[] accounts = StatePersister.load(stateFile);
        Account.restoreAccountCounter(accounts);
        for (Account account : accounts) {
            repository.save(account);
            concurrentAccounts.put(account.getAccountNumber(), account);
        }
        return accounts.length;
    }

    public synchronized void report() throws IOException {
        ReportGenerator.generate(Path.of("logs"), Path.of("reports", "report.txt"));
    }

    public void concurrentDemo() throws InterruptedException {
        Account demo = new SavingsAccount("Thread Demo", 0, 0);
        demo.setTransactionMessagesEnabled(false);
        TransactionProcessor processor = new TransactionProcessor();
        processor.addAccount(demo);
        for (int i = 0; i < 10; i++) processor.submit(new AccountWorker(demo, 1000, 1));
        processor.stop();
        System.out.println("Threads: 10 | Deposits/thread: 1000 | Amount: Rs. 1");
        System.out.println("Expected: Rs. 10000 | Actual: Rs. " + demo.getBalance());
        System.out.println(demo.getBalance() == 10000 ? "PASS: synchronized transactions" : "FAIL");
    }
}
