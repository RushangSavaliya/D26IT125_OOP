public class OutOfStockException extends Exception {

	private int shortfall;

	public OutOfStockException(int shortfall) {
		super("Insufficient stock. Shortfall: " + shortfall);
		this.shortfall = shortfall;
	}

	public int getShortfall() {
		return shortfall;
	}
}
