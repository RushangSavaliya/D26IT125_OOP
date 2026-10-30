public class CounterRace {

	public static void main(String[] args) throws InterruptedException {

		int numberOfThreads = 10;
		int incrementsPerThread = 100000;

		int expected = numberOfThreads * incrementsPerThread;

		// WITHOUT SYNCHRONIZATION

		Counter counter1 = new Counter();

		CounterThread[] threads1 = new CounterThread[numberOfThreads];

		for (int i = 0; i < numberOfThreads; i++) {

			threads1[i] = new CounterThread(counter1, incrementsPerThread, false);

			threads1[i].start();
		}

		// Wait for all threads to finish
		for (CounterThread thread : threads1) {
			thread.join();
		}

		System.out.println("WITHOUT SYNCHRONIZATION");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + counter1.getCount());

		// WITH SYNCHRONIZATION

		Counter counter2 = new Counter();

		CounterThread[] threads2 = new CounterThread[numberOfThreads];

		for (int i = 0; i < numberOfThreads; i++) {

			threads2[i] = new CounterThread(counter2, incrementsPerThread, true);

			threads2[i].start();
		}

		// Wait for all threads to finish
		for (CounterThread thread : threads2) {
			thread.join();
		}

		System.out.println("\nWITH SYNCHRONIZATION");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + counter2.getCount());
	}
}
