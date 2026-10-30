package model;

import java.io.Serializable;

public class CurrentAccount extends Account
    implements Premium, Serializable {

	private static final long serialVersionUID = 1L;

	private long overdraftLimit;

	public CurrentAccount(String ownerName, long openingBalance,
	    long overdraftLimit) {
		super(ownerName, openingBalance);
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
