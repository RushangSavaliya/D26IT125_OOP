
import java.util.*;

public class WordFrequency {

	public static void main(String[] args) {

		String paragraph = "java is easy java is powerful java is popular";

		// Split paragraph into words
		String[] words = paragraph.toLowerCase().split("\\s+");

		// Count word frequency using HashMap
		HashMap<String, Integer> map = new HashMap<>();

		for (String word : words) {
			map.put(word, map.getOrDefault(word, 0) + 1);
		}

		// Sort entries by frequency (highest first)
		List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

		list.sort(new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
				return b.getValue().compareTo(a.getValue());
			}
		});

		// Display sorted word frequencies
		System.out.println("Word Frequencies:");

		for (Map.Entry<String, Integer> entry : list) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
