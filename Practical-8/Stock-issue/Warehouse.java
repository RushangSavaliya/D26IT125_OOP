import java.util.*;

public class Warehouse {

	private Map<String, Integer> stock = new HashMap<>();

	public Warehouse() {
		stock.put("Laptop", 10);
		stock.put("Mouse", 5);
		stock.put("Keyboard", 8);
	}

	public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {

		// Check quantity
		if (qty <= 0) {
			throw new InvalidQuantityException("Quantity must be greater than 0.");
		}

		// Check whether item exists
		if (!stock.containsKey(item)) {
			throw new OutOfStockException(qty);
		}

		int available = stock.get(item);

		// Check stock
		if (qty > available) {
			int shortfall = qty - available;
			throw new OutOfStockException(shortfall);
		}

		// Issue the items
		stock.put(item, available - qty);

		System.out.println("Issued " + qty + " " + item + "(s). Remaining stock: " + (available - qty));
	}

	public static void main(String[] args) {

		Warehouse warehouse = new Warehouse();

		// List of requests
		String[][] requests = {{"Laptop", "3"}, {"Mouse", "10"}, {"Keyboard", "0"}, {"Laptop", "2"},
				{"Mouse", "-5"}, {"Keyboard", "4"}};

		// Process every request
		for (String[] request : requests) {

			String item = request[0];
			int qty = Integer.parseInt(request[1]);

			System.out.println("\nRequest: " + item + ", Quantity: " + qty);

			try {

				warehouse.issue(item, qty);

			} catch (OutOfStockException e) {

				System.out.println("Out of stock. Shortfall: " + e.getShortfall());

			} catch (InvalidQuantityException e) {

				System.out.println("Invalid quantity: " + e.getMessage());
			}
		}

		System.out.println("\nAll requests processed.");
	}
}
