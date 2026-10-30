package service;

import exception.BankException;
import model.Account;

public class AccountWorker implements Runnable {

	private final Account account;
	private final int times;
	private final long amount;

	public AccountWorker(Account account, int times, long amount) {
		this.account = account;
		this.times = times;
		this.amount = amount;
	}

	@Override
	public void run() {
		for (int i = 0; i < times; i++) {
			try {
				account.deposit(amount);
			} catch (BankException e) {
				System.out.println(
				    Thread.currentThread().getName() +
				    " failed: " + e.getMessage());
				return;
			}

			// Give the other threads a chance to interleave.
			Thread.yield();
		}
	}
}
