public class SeatBookingRace {

	public static void main(String[] args) throws InterruptedException {

		int numberOfThreads = 10;

		// WITHOUT SYNCHRONIZATION

		SeatBooking booking1 = new SeatBooking();

		BookingThread[] threads1 = new BookingThread[numberOfThreads];

		int successfulBookings = 0;

		System.out.println("WITHOUT SYNCHRONIZATION");

		for (int i = 0; i < numberOfThreads; i++) {

			threads1[i] = new BookingThread(booking1, false);

			threads1[i].setName("Thread-" + (i + 1));
			threads1[i].start();
		}

		for (BookingThread thread : threads1) {
			thread.join();
		}

		// Count remaining seats to calculate successful bookings
		successfulBookings = 5 - booking1.getSeatsLeft();

		System.out.println("Successful bookings: " + successfulBookings);

		System.out.println("Seats left: " + booking1.getSeatsLeft());

		// WITH SYNCHRONIZATION

		SeatBooking booking2 = new SeatBooking();

		BookingThread[] threads2 = new BookingThread[numberOfThreads];

		System.out.println("\nWITH SYNCHRONIZATION");

		for (int i = 0; i < numberOfThreads; i++) {

			threads2[i] = new BookingThread(booking2, true);

			threads2[i].setName("Thread-" + (i + 1));
			threads2[i].start();
		}

		for (BookingThread thread : threads2) {
			thread.join();
		}

		successfulBookings = 5 - booking2.getSeatsLeft();

		System.out.println("Successful bookings: " + successfulBookings);

		System.out.println("Seats left: " + booking2.getSeatsLeft());
	}
}
