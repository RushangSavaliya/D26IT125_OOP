package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor {

	private final ExecutorService pool;

	public TransactionProcessor() {
		pool = Executors.newFixedThreadPool(4);
	}

	public void submit(Runnable task) {
		pool.execute(task);
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
