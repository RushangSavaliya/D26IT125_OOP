package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

import exception.InsufficientFundsException;
import model.Account;

class BankServiceTest {

	private static final String PAN = "ABCDE1234F";

	private BankService newService() {
		return new BankService(new Repository<>(), null);
	}

	@Test
	void depositIncreasesBalance() throws Exception {
		BankService service = newService();
		Account account =
		    service.openAccount("Asha", PAN, "savings", 5000);

		service.deposit(account.getAccountNumber(), 2000);

		assertEquals(7000, account.getBalance());
	}

	@Test
	void withdrawalOverTheLimitThrows() throws Exception {
		BankService service = newService();
		Account account =
		    service.openAccount("Bala", PAN, "savings", 1000);

		assertThrows(InsufficientFundsException.class,
		    () -> service.withdraw(account.getAccountNumber(), 500));

		assertEquals(1000, account.getBalance());
	}

	@Test
	void concurrentDepositsKeepBalanceCorrect() throws Exception {
		BankService service = newService();
		Account account =
		    service.openAccount("Chitra", PAN, "current", 0);

		int threads = 10;
		int depositsPerThread = 1000;
		ExecutorService pool = Executors.newFixedThreadPool(threads);
		ArrayList<Callable<Void>> tasks = new ArrayList<>();

		for (int t = 0; t < threads; t++) {
			tasks.add(() -> {
				for (int i = 0; i < depositsPerThread; i++) {
					service.deposit(
					    account.getAccountNumber(), 1);
				}

				return null;
			});
		}

		for (Future<Void> result : pool.invokeAll(tasks)) {
			result.get();
		}

		pool.shutdown();

		assertEquals(threads * depositsPerThread,
		    account.getBalance());
	}
}
