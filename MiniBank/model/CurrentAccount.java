package model;

public class CurrentAccount extends Account {

	private long overdraftLimit;

	public CurrentAccount(String ownerName, long openingBalance,
	    long overdraftLimit) {
		super(ownerName, openingBalance);
		this.overdraftLimit = overdraftLimit;
	}

	public CurrentAccount(String accountNumber, String ownerName,
	    long balance, long overdraftLimit) {
		super(accountNumber, ownerName, balance);
		this.overdraftLimit = overdraftLimit;
	}

	@Override
	public double interestRate() {
		return 0.0;
	}

	@Override
	public boolean canWithdraw(long amount) {
		return getBalance() - amount >= -overdraftLimit;
	}

	public long getOverdraftLimit() {
		return overdraftLimit;
	}
}
