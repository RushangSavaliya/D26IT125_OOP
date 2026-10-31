package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import exception.BankException;
import model.Account;
import model.BankInfo;
import model.MenuOption;
import util.StatePersister;

public class MiniBank {

	private static final BankInfo BANK =
	    new BankInfo("MiniBank", "Main Branch");
	private static final Path DATA_DIR = Path.of("data");
	private static final Path ACCOUNTS_FILE =
	    DATA_DIR.resolve("accounts.txt");
	private static final int EXIT_CHOICE = 9;

	public static void main(String[] args) {
		printHeader();

		Repository<Account> repository = new Repository<>();
		loadAccounts(repository);

		BankService service =
		    new BankService(repository, DATA_DIR);

		try {
			runMenu(service);
		} finally {
			saveAccounts(repository);
		}
	}

	private static void loadAccounts(Repository<Account> repository) {
		if (!Files.exists(ACCOUNTS_FILE)) {
			System.out.println(
			    "No saved accounts. Use option 1 to open one.\n");
			return;
		}

		try {
			Account[] loaded = StatePersister.load(ACCOUNTS_FILE);
			Account.syncCounter(loaded);

			for (Account account : loaded) {
				repository.save(account);
			}

			System.out.println("Loaded " + loaded.length +
			    " account(s).\n");
		} catch (IOException e) {
			System.out.println("Could not load accounts: " +
			    e.getMessage() + "\n");
		}
	}

	private static void saveAccounts(Repository<Account> repository) {
		ArrayList<Account> accounts = repository.findAll();
		Collections.sort(accounts);

		try {
			StatePersister.save(accounts.toArray(new Account[0]),
			    ACCOUNTS_FILE);
			System.out.println("Saved " + accounts.size() +
			    " account(s).");
		} catch (IOException e) {
			System.out.println("Could not save accounts: " +
			    e.getMessage());
		}
	}

	private static void runMenu(BankService service) {
		try (Scanner sc = new Scanner(System.in)) {

			boolean running = true;

			while (running) {
				printMenu();
				int choice = readChoice(sc);

				MenuOption option = switch (choice) {
					case 1 -> MenuOption.OPEN_ACCOUNT;
					case 2 -> MenuOption.DEPOSIT;
					case 3 -> MenuOption.WITHDRAW;
					case 4 -> MenuOption.TRANSFER;
					case 5 -> MenuOption.STATEMENT;
					case 6 -> MenuOption.LIST_ACCOUNTS;
					case 7 -> MenuOption.BATCH;
					case 8 -> MenuOption.REPORT;
					case 9 -> MenuOption.EXIT;
					default -> null;
				};

				if (option == null) {
					System.out.println("Invalid choice. Enter 1-" +
					    EXIT_CHOICE + ".\n");
					continue;
				}

				try {
					switch (option) {
						case OPEN_ACCOUNT ->
						    openAccount(service, sc);
						case DEPOSIT ->
						    deposit(service, sc);
						case WITHDRAW ->
						    withdraw(service, sc);
						case TRANSFER ->
						    transfer(service, sc);
						case STATEMENT ->
						    statement(service, sc);
						case LIST_ACCOUNTS ->
						    listAccounts(service);
						case BATCH ->
						    service.processBatch();
						case REPORT ->
						    report(service);
						case EXIT -> running = false;
					}
				} catch (BankException | IOException e) {
					System.out.println("Error: " +
					    e.getMessage() + "\n");
				}
			}
		}

		System.out.println(
		    "Thank you for banking with MiniBank. Goodbye!");
	}

	private static void openAccount(BankService service, Scanner sc)
	    throws BankException {
		String name = readLine(sc, "Owner name: ");
		String pan = readLine(sc, "PAN (e.g. ABCDE1234F): ");
		String type = readLine(sc,
		    "Type (savings/current/fixed): ");

		if (type.isBlank()) {
			type = "savings";
		}

		long opening = readAmount(sc);
		Account account =
		    service.openAccount(name, pan, type, opening);

		System.out.println("Opened " + account.getAccountNumber() +
		    " for " + account.getOwnerName() + ".\n");
	}

	private static void deposit(BankService service, Scanner sc)
	    throws BankException {
		String number = readLine(sc, "Account number: ");
		long amount = readAmount(sc);

		service.deposit(number, amount);

		System.out.println("Deposited " + amount + ".\n");
	}

	private static void withdraw(BankService service, Scanner sc)
	    throws BankException {
		String number = readLine(sc, "Account number: ");
		long amount = readAmount(sc);

		service.withdraw(number, amount);

		System.out.println("Withdrew " + amount + ".\n");
	}

	private static void transfer(BankService service, Scanner sc)
	    throws BankException {
		String from = readLine(sc, "From account: ");
		String to = readLine(sc, "To account: ");
		long amount = readAmount(sc);

		service.transfer(from, to, amount);

		System.out.println("Transferred " + amount + ".\n");
	}

	private static void statement(BankService service, Scanner sc)
	    throws BankException {
		String number = readLine(sc, "Account number: ");

		System.out.println();
		System.out.println(service.statement(number));
		System.out.println();
	}

	private static void listAccounts(BankService service) {
		ArrayList<Account> accounts = service.listAccounts();

		if (accounts.isEmpty()) {
			System.out.println("No accounts yet.\n");
			return;
		}

		System.out.println("Accounts:");

		for (Account account : accounts) {
			System.out.println("  " + account);
		}

		System.out.println();
	}

	private static void report(BankService service) throws IOException {
		System.out.println(service.runReport());
		System.out.println("Report: " +
		    DATA_DIR.resolve("report.txt") + "\n");
	}

	private static void printHeader() {
		BankInfo bank = BANK;

		System.out.println("=".repeat(46));
		System.out.println("  " + bank.name() + " — " + bank.branch());
		System.out.println("=".repeat(46));
		System.out.println();
	}

	private static void printMenu() {
		System.out.println("1. Open Account");
		System.out.println("2. Deposit");
		System.out.println("3. Withdraw");
		System.out.println("4. Transfer");
		System.out.println("5. Statement");
		System.out.println("6. List Accounts");
		System.out.println("7. Run Batch");
		System.out.println("8. End-of-day Report");
		System.out.println("9. Exit");
		System.out.print("Choose an option: ");
	}

	private static int readChoice(Scanner sc) {
		while (true) {
			if (!sc.hasNextLine()) {
				return EXIT_CHOICE;
			}

			String text = sc.nextLine().trim();

			try {
				return Integer.parseInt(text);
			} catch (NumberFormatException e) {
				System.out.print(
				    "Enter 1-" + EXIT_CHOICE + ": ");
			}
		}
	}

	private static String readLine(Scanner sc, String prompt) {
		System.out.print(prompt);
		return sc.hasNextLine() ? sc.nextLine().trim() : "";
	}

	private static long readAmount(Scanner sc) {
		while (true) {
			String text = readLine(sc, "Amount: ");

			if (text.isEmpty()) {
				return 0;
			}

			try {
				return Long.parseLong(text);
			} catch (NumberFormatException e) {
				System.out.println("Enter a whole number.");
			}
		}
	}
}
