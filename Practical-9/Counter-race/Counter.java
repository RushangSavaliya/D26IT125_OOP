public class Counter {

	private int count = 0;

	// Unsynchronized increment
	public void increment() {
		count++;
	}

	// Synchronized increment
	public synchronized void synchronizedIncrement() {
		count++;
	}

	public int getCount() {
		return count;
	}
}
