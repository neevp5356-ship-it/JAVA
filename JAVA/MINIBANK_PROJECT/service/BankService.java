package service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import model.*;
import exception.*;
import util.*;

public class BankService {
    private final Repository<Account> accountRepository = new Repository<>();
    private final ConcurrentHashMap<String, Account> concurrentStore = new ConcurrentHashMap<>();
    private final ArrayList<String> transactionHistory = new ArrayList<>();
    private final Path dataFile = Path.of("data", "accounts.dat");
    private final Path logFile = Path.of("logs", "transactions.log");

    public BankService() {
        loadState();
    }

    public synchronized void loadState() {
        ArrayList<Account> loaded = StatePersister.load(dataFile);
        for (Account acc : loaded) {
            accountRepository.save(acc);
            concurrentStore.put(acc.getAccountNumber(), acc);
        }
    }

    public synchronized void saveState() {
        StatePersister.save(accountRepository.findAll(), dataFile);
    }

    public synchronized Account openAccount(String type, String ownerName, long openingBalance, long extraParam) throws BankException {
        Account account = switch (type.toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(ownerName, openingBalance, extraParam);
            case "CURRENT" -> new CurrentAccount(ownerName, openingBalance, extraParam);
            case "FD" -> new FixedDepositAccount(ownerName, openingBalance, extraParam == 1);
            default -> throw new BankException("Unknown account type: " + type);
        };

        String[] errors = AnnotationValidator.validate(account);
        if (errors.length > 0) {
            throw new InvalidAmountException(String.join(", ", errors));
        }

        accountRepository.save(account);
        concurrentStore.put(account.getAccountNumber(), account);
        saveState();
        transactionHistory.add("OPEN " + account.getAccountNumber() + " " + ownerName);
        TransactionLog.append(logFile, "OPEN " + account.getAccountNumber() + " " + openingBalance);
        return account;
    }

    public void deposit(String accountNumber, long amount) throws BankException {
        Account acc = getAccount(accountNumber);
        synchronized (acc) {
            acc.deposit(amount);
        }
        saveState();
        transactionHistory.add("DEPOSIT " + accountNumber + " " + amount);
        TransactionLog.append(logFile, "DEPOSIT " + accountNumber + " " + amount);
    }

    public void withdraw(String accountNumber, long amount) throws BankException {
        Account acc = getAccount(accountNumber);
        synchronized (acc) {
            acc.withdraw(amount);
        }
        saveState();
        transactionHistory.add("WITHDRAW " + accountNumber + " " + amount);
        TransactionLog.append(logFile, "WITHDRAW " + accountNumber + " " + amount);
    }

    public void transfer(String fromAccNum, String toAccNum, long amount) throws BankException {
        if (fromAccNum.equals(toAccNum)) {
            throw new BankException("Cannot transfer to the same account.");
        }
        Account from = getAccount(fromAccNum);
        Account to = getAccount(toAccNum);

        // Consistent lock ordering to prevent deadlock
        Account firstLock = fromAccNum.compareTo(toAccNum) < 0 ? from : to;
        Account secondLock = firstLock == from ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
        saveState();
        transactionHistory.add("TRANSFER " + fromAccNum + " -> " + toAccNum + " " + amount);
        TransactionLog.append(logFile, "TRANSFER " + fromAccNum + " " + toAccNum + " " + amount);
    }

    public String getStatement(String accountNumber) throws BankException {
        Account acc = getAccount(accountNumber);
        return StatementFormatter.buildStatement(acc);
    }

    public ArrayList<Account> listAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account acc = accountRepository.findById(accountNumber);
        if (acc == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return acc;
    }

    public String generateReport() {
        return ReportGenerator.generateDailyReport(Path.of("logs"), Path.of("data", "daily_report.txt"));
    }
}
