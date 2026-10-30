package service;

public class TransactionBuffer {

	private final String[] buffer;
	private int count;
	private int putIndex;
	private int takeIndex;

	public TransactionBuffer(int capacity) {
		this.buffer = new String[capacity];
	}

	public synchronized void put(String line) throws InterruptedException {
		while (count == buffer.length) {
			wait();
		}

		buffer[putIndex] = line;
		putIndex = (putIndex + 1) % buffer.length;
		count++;
		notifyAll();
	}

	public synchronized String take() throws InterruptedException {
		while (count == 0) {
			wait();
		}

		String line = buffer[takeIndex];
		takeIndex = (takeIndex + 1) % buffer.length;
		count--;
		notifyAll();

		return line;
	}
}
