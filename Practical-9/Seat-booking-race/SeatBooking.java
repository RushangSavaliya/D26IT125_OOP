public class SeatBooking {

	private int seatsLeft = 5;

	// Without synchronization
	public boolean bookWithoutSync() {

		if (seatsLeft > 0) {
			// Small delay makes the race condition easier to observe
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			seatsLeft--;
			return true;
		}

		return false;
	}

	// With synchronization
	public synchronized boolean book() {

		if (seatsLeft > 0) {
			seatsLeft--;
			return true;
		}

		return false;
	}

	public int getSeatsLeft() {
		return seatsLeft;
	}
}
