public class CinemaShow {
	private String title;
	private int seatsAvailable;
	private final int capacity;

	private static int totalBooked = 0;

	public CinemaShow(String title, int capacity) {
		this.title = title;
		this.capacity = capacity;
		this.seatsAvailable = capacity;
	}

	public CinemaShow(String title) { this(title, 100); }

	public boolean book(int n) {
		if (n <= seatsAvailable) {
			seatsAvailable -= n;
			totalBooked += n;
			return true;
		}

		return false;
	}

	public void cancel(int n) {
		seatsAvailable += n;

		if (seatsAvailable > capacity) {
			seatsAvailable = capacity;
		}
	}

	public int getSeatsAvailable() { return seatsAvailable; }

	public static int getTotalBooked() { return totalBooked; }

	public static void main(String[] args) {
		CinemaShow nightcrawler = new CinemaShow("Nightcrawler", 50);
		CinemaShow drive = new CinemaShow("Drive");

		System.out.println("=== Nightcrawler ===");
		System.out.println("Available seats: " +
				   nightcrawler.getSeatsAvailable());

		System.out.println(
		    "Book 20: " + nightcrawler.book(20) +
		    " | Seats: " + nightcrawler.getSeatsAvailable());

		System.out.println(
		    "Book 40: " + nightcrawler.book(40) +
		    " | Seats: " + nightcrawler.getSeatsAvailable());

		nightcrawler.cancel(10);
		System.out.println("Cancel 10 | Seats: " +
				   nightcrawler.getSeatsAvailable());

		nightcrawler.cancel(100);
		System.out.println("Cancel 100 | Seats: " +
				   nightcrawler.getSeatsAvailable());

		System.out.println("\n=== Drive ===");
		System.out.println("Available seats: " +
				   drive.getSeatsAvailable());

		System.out.println("Book 50: " + drive.book(50) +
				   " | Seats: " + drive.getSeatsAvailable());

		System.out.println("Book 60: " + drive.book(60) +
				   " | Seats: " + drive.getSeatsAvailable());

		System.out.println("\nTotal booked: " +
				   CinemaShow.getTotalBooked());
	}
}
