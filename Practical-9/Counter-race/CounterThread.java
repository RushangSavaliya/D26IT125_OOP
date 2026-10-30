public class CounterThread extends Thread {

	private Counter counter;
	private int repetitions;
	private boolean useSynchronization;

	public CounterThread(Counter counter, int repetitions, boolean useSynchronization) {
		this.counter = counter;
		this.repetitions = repetitions;
		this.useSynchronization = useSynchronization;
	}

	@Override
	public void run() {

		for (int i = 0; i < repetitions; i++) {

			if (useSynchronization) {
				counter.synchronizedIncrement();
			} else {
				counter.increment();
			}
		}
	}
}
