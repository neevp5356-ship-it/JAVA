package model;

import java.io.Serializable;
import java.util.Objects;
import model.annotation.Id;
import model.annotation.Positive;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public abstract class Account implements Serializable, Transactable, InterestBearing, Comparable<Account> {
    private static final long serialVersionUID = 1L;
    private static long accountCounter = 0;

    @Id
    private final String accountNumber;
    private String ownerName;
    @Positive(message = "balance must be > 0")
    protected long balance;
    private boolean active;

    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    private synchronized static String generateAccountNumber() {
        return String.format("AC%04d", ++accountCounter);
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public synchronized long getBalance() { return balance; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (!canWithdraw(amount)) {
            long shortfall = amount - balance;
            if (shortfall < 0) shortfall = amount; // in case of FixedDeposit
            throw new InsufficientFundsException(shortfall);
        }
        this.balance -= amount;
        return true;
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Bal: " + balance + " | Active: " + active;
    }
}
