package model;

public class CurrentAccount extends Account implements Premium {

	private long overdraftLimit;

	public CurrentAccount(String ownerName, long openingBalance,
	    long overdraftLimit) {
		super(ownerName, openingBalance);
		this.overdraftLimit = overdraftLimit;
	}

	public double interestRate() {
		return 0.0;
	}

	public boolean canWithdraw(long amount) {
		return getBalance() - amount >= -overdraftLimit;
	}

	public long getOverdraftLimit() {
		return overdraftLimit;
	}
}
