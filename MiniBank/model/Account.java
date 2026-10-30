package model;

import model.annotation.Id;
import model.annotation.Positive;

public abstract class Account implements Transactable, InterestBearing,
    Cloneable {

	private static long accountCounter = 0;

	@Id
	private final String accountNumber;
	private String ownerName;
	@Positive
	private long balance;
	private boolean active;

	public Account(String ownerName, long openingBalance) {
		this.accountNumber = generateAccountNumber();
		this.ownerName = ownerName;
		this.balance = openingBalance;
		this.active = true;
	}

	public Account(String ownerName) {
		this(ownerName, 0);
	}

	private static String generateAccountNumber() {
		accountCounter++;
		return String.format("AC%04d", accountCounter);
	}

	@Override
	public abstract double interestRate();

	public abstract boolean canWithdraw(long amount);

	@Override
	public void deposit(long amount) {
		balance += amount;
	}

	@Override
	public boolean withdraw(long amount) {
		if (!canWithdraw(amount)) {
			return false;
		}

		balance -= amount;
		return true;
	}

	@Override
	public Account clone() {
		try {
			return (Account) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Account is Cloneable", e);
		}
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
}
