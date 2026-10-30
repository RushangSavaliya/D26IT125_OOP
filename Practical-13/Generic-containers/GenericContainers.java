class Box<T> {
	private T value;

	public void set(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}
}

class Pair<A, B> {
	private A first;
	private B second;

	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}

	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}
}

public class GenericContainers {

	// Generic method to return the first element of an array
	public static <T> T getFirst(T[] array) {
		return array[0];
	}

	public static void main(String[] args) {

		// Box with Integer
		Box<Integer> intBox = new Box<>();
		intBox.set(100);
		System.out.println("Box Integer: " + intBox.get());

		// Box with String
		Box<String> strBox = new Box<>();
		strBox.set("Hello Java");
		System.out.println("Box String: " + strBox.get());

		// Pair with different types
		Pair<String, Integer> student = new Pair<>("Rahul", 101);
		System.out.println("Pair: " + student.getFirst() + ", " + student.getSecond());

		// Generic method with different arrays
		String[] names = {"Amit", "Rahul", "Priya"};
		Integer[] numbers = {10, 20, 30};

		System.out.println("First name: " + getFirst(names));
		System.out.println("First number: " + getFirst(numbers));
	}
}
