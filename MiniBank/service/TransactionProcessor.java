package service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import model.Account;
import model.TransactionType;

public class TransactionProcessor {

	private final ExecutorService pool;
	private final ConcurrentHashMap<String, Account> accounts =
	    new ConcurrentHashMap<>();

	public TransactionProcessor() {
		pool = Executors.newFixedThreadPool(4);
	}

	public void register(Account account) {
		accounts.put(account.getAccountNumber(), account);
	}

	public void submit(String accountNumber, TransactionType type,
	    long amount) {
		pool.execute(() -> {
			Account account = accounts.get(accountNumber);

			if (account == null) {
				System.out.println(
				    Thread.currentThread().getName() +
				    " account not found: " + accountNumber);
				return;
			}

			new TransactionTask(account, type, amount).run();
		});
	}

	public void stop() {
		pool.shutdown();

		try {
			if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
				System.out.println(
				    "Processor timed out; forcing shutdown");
				pool.shutdownNow();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			pool.shutdownNow();
		}
	}
}
