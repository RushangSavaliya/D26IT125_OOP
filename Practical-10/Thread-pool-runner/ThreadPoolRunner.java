
import java.util.concurrent.*;

public class ThreadPoolRunner {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(3);

		for (int i = 1; i <= 10; i++) {

			final int id = i;

			Runnable task = () -> {
				System.out.println("Task " + id + " executed by " + Thread.currentThread().getName());

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			};

			executor.submit(task);
		}

		executor.shutdown();

		try {
			if (executor.awaitTermination(30, TimeUnit.SECONDS)) {
				System.out.println("All tasks completed.");
			} else {
				System.out.println("Tasks did not finish in time.");
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}
