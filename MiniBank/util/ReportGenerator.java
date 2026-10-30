package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {

	private ReportGenerator() {
	}

	public static String generate(Path logsDir, Path reportFile)
	    throws IOException {
		long totalDeposits = 0;
		long totalWithdrawals = 0;

		StringBuilder summary = new StringBuilder();
		summary.append("End-of-day report\n");
		summary.append("=================\n");

		try (DirectoryStream<Path> logs =
		    Files.newDirectoryStream(logsDir, "*.log")) {

			for (Path log : logs) {
				BasicFileAttributes attributes =
				    Files.readAttributes(log,
					BasicFileAttributes.class);

				if (attributes.size() == 0) {
					continue;
				}

				long[] totals = readTotals(log);

				totalDeposits += totals[0];
				totalWithdrawals += totals[1];

				summary.append(log.getFileName())
				    .append(": size=").append(attributes.size())
				    .append(" bytes, modified=")
				    .append(attributes.lastModifiedTime())
				    .append(", deposits=").append(totals[0])
				    .append(", withdrawals=").append(totals[1])
				    .append('\n');
			}
		}

		summary.append("-----------------\n");
		summary.append("Total deposits: ")
		    .append(totalDeposits).append('\n');
		summary.append("Total withdrawals: ")
		    .append(totalWithdrawals).append('\n');
		summary.append("Net change: ")
		    .append(totalDeposits - totalWithdrawals).append('\n');

		Path parent = reportFile.getParent();

		if (parent != null) {
			Files.createDirectories(parent);
		}

		Files.writeString(reportFile, summary.toString());

		return summary.toString();
	}

	private static long[] readTotals(Path log) throws IOException {
		long deposits = 0;
		long withdrawals = 0;

		try (BufferedReader reader = Files.newBufferedReader(log)) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] parts = line.trim().split("\\s+");

				if (parts.length < 3) {
					continue;
				}

				long amount;

				try {
					amount = Long.parseLong(parts[2]);
				} catch (NumberFormatException e) {
					continue;
				}

				switch (parts[0].toUpperCase()) {
					case "DEPOSIT" -> deposits += amount;
					case "WITHDRAW" -> withdrawals += amount;
					default -> {
					}
				}
			}
		}

		return new long[] {deposits, withdrawals};
	}
}
