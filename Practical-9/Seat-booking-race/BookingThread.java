public class BookingThread extends Thread {

	private SeatBooking booking;
	private boolean useSynchronization;

	public BookingThread(SeatBooking booking, boolean useSynchronization) {
		this.booking = booking;
		this.useSynchronization = useSynchronization;
	}

	@Override
	public void run() {

		boolean success;

		if (useSynchronization) {
			success = booking.book();
		} else {
			success = booking.bookWithoutSync();
		}

		if (success) {
			System.out.println(Thread.currentThread().getName() + " successfully booked a seat.");
		} else {
			System.out.println(Thread.currentThread().getName() + " failed - no seats left.");
		}
	}
}
