package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.annotation.Id;

public abstract class Account implements Transactable, InterestBearing,
    Comparable<Account> {

	private static long accountCounter = 0;

	@Id
	private final String accountNumber;
	private String ownerName;
	private long balance;
	private boolean active;

	public Account(String ownerName, long openingBalance) {
		this.accountNumber = generateAccountNumber();
		this.ownerName = ownerName;
		this.balance = openingBalance;
		this.active = true;
	}

	protected Account(String accountNumber, String ownerName,
	    long balance) {
		this.accountNumber = accountNumber;
		this.ownerName = ownerName;
		this.balance = balance;
		this.active = true;
	}

	private static String generateAccountNumber() {
		accountCounter++;
		return String.format("AC%04d", accountCounter);
	}

	public static synchronized void syncCounter(Account[] accounts) {
		if (accounts == null) {
			return;
		}

		for (Account account : accounts) {
			String digits = account.accountNumber.substring(2);

			try {
				long value = Long.parseLong(digits);
				accountCounter = Math.max(accountCounter, value);
			} catch (NumberFormatException e) {
				// Ignore ids that are not in the ACdddd format.
			}
		}
	}

	@Override
	public abstract double interestRate();

	public abstract boolean canWithdraw(long amount);

	@Override
	public synchronized void deposit(long amount)
	    throws InvalidAmountException {
		if (amount <= 0) {
			throw new InvalidAmountException(amount);
		}

		balance += amount;
	}

	@Override
	public synchronized boolean withdraw(long amount)
	    throws InsufficientFundsException, InvalidAmountException {
		if (amount <= 0) {
			throw new InvalidAmountException(amount);
		}

		if (!canWithdraw(amount)) {
			throw new InsufficientFundsException(
			    Math.max(amount - balance, 0));
		}

		balance -= amount;
		return true;
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
	public String toString() {
		return accountNumber + " | " + ownerName + " | " + balance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof Account)) {
			return false;
		}

		Account other = (Account) o;
		return accountNumber.equals(other.accountNumber);
	}

	@Override
	public int hashCode() {
		return accountNumber.hashCode();
	}

	@Override
	public int compareTo(Account other) {
		return accountNumber.compareTo(other.accountNumber);
	}
}
