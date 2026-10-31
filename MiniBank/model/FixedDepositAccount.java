package model;

public class FixedDepositAccount extends Account {

	public FixedDepositAccount(String ownerName, long openingBalance) {
		super(ownerName, openingBalance);
	}

	public FixedDepositAccount(String accountNumber, String ownerName,
	    long balance) {
		super(accountNumber, ownerName, balance);
	}

	@Override
	public double interestRate() {
		return 7.0;
	}

	@Override
	public boolean canWithdraw(long amount) {
		return false;
	}
}
