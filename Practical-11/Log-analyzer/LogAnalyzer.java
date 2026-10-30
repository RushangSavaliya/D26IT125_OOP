
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class LogAnalyzer {

	public static void main(String[] args) {

		try {
			// Create sample log files
			Path file1 = Paths.get("log1.txt");
			Path file2 = Paths.get("log2.txt");
			Path file3 = Paths.get("log3.txt");

			Files.write(file1, Arrays.asList("INFO: Application started",
					"ERROR: Database connection failed", "INFO: User logged in"));

			Files.write(file2, Arrays.asList("INFO: Server running", "ERROR: File not found",
					"WARNING: Low memory"));

			Files.write(file3, Arrays.asList("INFO: Application stopped", "ERROR: Network failure"));

			// Store all file paths
			Path[] files = {file1, file2, file3};

			int totalLines = 0;
			int keywordCount = 0;

			String keyword = "ERROR";

			// Analyze each file
			for (Path path : files) {

				int fileLines = 0;

				// Read file line by line
				try (BufferedReader reader = Files.newBufferedReader(path)) {

					String line;

					while ((line = reader.readLine()) != null) {

						totalLines++;
						fileLines++;

						if (line.toLowerCase().contains(keyword.toLowerCase())) {
							keywordCount++;
						}
					}
				}

				// Read file attributes
				BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

				System.out.println("File: " + path);
				System.out.println("Size: " + attr.size() + " bytes");
				System.out.println("Lines: " + fileLines);
				System.out.println();
			}

			// Display summary
			System.out.println("Total Lines: " + totalLines);
			System.out.println("Lines containing " + keyword + ": " + keywordCount);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
