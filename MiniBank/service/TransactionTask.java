package service;

import exception.BankException;
import model.Account;
import model.TransactionType;

public class TransactionTask implements Runnable {

	private final Account account;
	private final TransactionType type;
	private final long amount;

	public TransactionTask(Account account, TransactionType type,
	    long amount) {
		this.account = account;
		this.type = type;
		this.amount = amount;
	}

	@Override
	public void run() {
		try {
			switch (type) {
				case DEPOSIT -> account.deposit(amount);
				case WITHDRAW -> account.withdraw(amount);
				default -> throw new BankException(
				    "Unsupported batch type: " + type);
			}

			System.out.println(Thread.currentThread().getName() +
			    " " + type + " " + amount + " -> balance " +
			    account.getBalance());
		} catch (BankException e) {
			System.out.println(Thread.currentThread().getName() +
			    " " + type + " " + amount + " failed: " +
			    e.getMessage());
		}
	}
}
