package util;

import model.Command;
import model.TransactionType;

public class CommandParser {

	private CommandParser() {
	}

	public static Command parse(String line) {
		if (line == null || line.isBlank()) {
			throw new IllegalArgumentException("Command line is empty");
		}

		String[] parts = line.trim().split("\\s+");

		if (parts.length != 3) {
			throw new IllegalArgumentException(
			    "Expected: TYPE ACCOUNT AMOUNT, got " +
			    parts.length + " part(s)");
		}

		TransactionType type = parseType(parts[0]);
		long amount = parseAmount(parts[2]);

		return new Command(type, parts[1], amount);
	}

	private static TransactionType parseType(String token) {
		return switch (token.toUpperCase()) {
			case "DEPOSIT" -> TransactionType.DEPOSIT;
			case "WITHDRAW" -> TransactionType.WITHDRAW;
			case "TRANSFER" -> TransactionType.TRANSFER;
			default -> throw new IllegalArgumentException(
			    "Unknown transaction type: " + token);
		};
	}

	private static long parseAmount(String token) {
		try {
			return Long.parseLong(token);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
			    "Amount is not a whole number: " + token, e);
		}
	}
}
