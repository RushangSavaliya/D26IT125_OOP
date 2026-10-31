package model;

public class SavingsAccount extends Account {

	private long minBalance;

	public SavingsAccount(String ownerName, long openingBalance,
	    long minBalance) {
		super(ownerName, openingBalance);
		this.minBalance = minBalance;
	}

	public SavingsAccount(String accountNumber, String ownerName,
	    long balance, long minBalance) {
		super(accountNumber, ownerName, balance);
		this.minBalance = minBalance;
	}

	@Override
	public double interestRate() {
		return 4.0;
	}

	@Override
	public boolean canWithdraw(long amount) {
		return getBalance() - amount >= minBalance;
	}

	public long getMinBalance() {
		return minBalance;
	}
}
