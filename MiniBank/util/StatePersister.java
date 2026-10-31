package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;

public class StatePersister {

	private StatePersister() {
	}

	public static void save(Account[] accounts, Path file)
	    throws IOException {
		Path parent = file.getParent();

		if (parent != null) {
			Files.createDirectories(parent);
		}

		StringBuilder text = new StringBuilder();

		for (Account account : accounts) {
			String type;
			long extra = 0;

			if (account instanceof SavingsAccount savings) {
				type = "SAVINGS";
				extra = savings.getMinBalance();
			} else if (account instanceof CurrentAccount current) {
				type = "CURRENT";
				extra = current.getOverdraftLimit();
			} else if (account instanceof FixedDepositAccount) {
				type = "FIXED";
			} else {
				throw new IOException("Unsupported account type: " +
				    account.getClass().getName());
			}

			text.append(type).append('|')
			    .append(account.getAccountNumber()).append('|')
			    .append(account.getOwnerName()).append('|')
			    .append(account.getBalance()).append('|')
			    .append(extra).append(System.lineSeparator());
		}

		Files.writeString(file, text.toString());
	}

	public static Account[] load(Path file) throws IOException {
		ArrayList<Account> accounts = new ArrayList<>();

		for (String line : Files.readAllLines(file)) {
			if (line.isBlank()) {
				continue;
			}

			String[] parts = line.split("\\|", -1);

			if (parts.length != 5) {
				throw new IOException("Invalid account line: " +
				    line);
			}

			try {
				long balance = Long.parseLong(parts[3]);
				long extra = Long.parseLong(parts[4]);

				accounts.add(switch (parts[0]) {
					case "SAVINGS" ->
					    new SavingsAccount(parts[1],
						parts[2], balance, extra);
					case "CURRENT" ->
					    new CurrentAccount(parts[1],
						parts[2], balance, extra);
					case "FIXED" ->
					    new FixedDepositAccount(parts[1],
						parts[2], balance);
					default -> throw new IOException(
					    "Unknown account type: " + parts[0]);
				});
			} catch (NumberFormatException e) {
				throw new IOException(
				    "Invalid number in account line: " + line, e);
			}
		}

		return accounts.toArray(new Account[0]);
	}
}
