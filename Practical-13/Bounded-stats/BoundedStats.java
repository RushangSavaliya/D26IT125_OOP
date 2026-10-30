public class BoundedStats {

	// Generic method that accepts only Number types
	public static <N extends Number> double average(N[] values) {

		double sum = 0;

		for (N value : values) {
			sum += value.doubleValue();
		}

		return sum / values.length;
	}

	public static void main(String[] args) {

		Integer[] integers = {10, 20, 30, 40};
		Double[] doubles = {2.5, 5.5, 7.5};
		Long[] longs = {100L, 200L, 300L};

		System.out.println("Integer Average: " + average(integers));
		System.out.println("Double Average: " + average(doubles));
		System.out.println("Long Average: " + average(longs));
	}
}
