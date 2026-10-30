import java.util.Scanner;

public class MiniBank {

	private static final BankInfo BANK =
	    new BankInfo("MiniBank", "Main Branch");
	private static final int EXIT_CHOICE = 5;

	public static void main(String[] args) {

		printHeader();
		demonstrateAccounts();

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
					case 5 -> MenuOption.EXIT;
					default -> null;
				};

				if (option == null) {
					System.out.println(
					    "Invalid choice. Enter a number from 1 to " +
					    EXIT_CHOICE + ".\n");
					continue;
				}

				String message = switch (option) {
					case OPEN_ACCOUNT ->
					    "Open Account — to be implemented in a later lab";
					case DEPOSIT ->
					    "Deposit — to be implemented in a later lab";
					case WITHDRAW ->
					    "Withdraw — to be implemented in a later lab";
					case TRANSFER ->
					    "Transfer — to be implemented in a later lab";
					case EXIT -> null;
				};

				if (option == MenuOption.EXIT) {
					running = false;
				} else {
					System.out.println(message + "\n");
				}
			}
		}

		System.out.println(
		    "Thank you for banking with MiniBank. Goodbye!");
	}

	private static void demonstrateAccounts() {
		System.out.println("-- Customer ids --");
		Customer first = new Customer(
		    "Riya", "riya@example.com", "9876543210");
		Customer second = new Customer(
		    "Aarav", "aarav@example.com", "9123456780");
		System.out.println(
		    first.getCustomerId() + " | " + first.getName());
		System.out.println(
		    second.getCustomerId() + " | " + second.getName());
		System.out.println();

		System.out.println("-- Account demo --");
		Account[] accounts = {
			new Account("Riya", 5000),
			new Account("Aarav"),
			new Account("Meera", 2500)
		};

		Account riya = accounts[0];

		riya.deposit(2000);
		System.out.println(
		    "Deposit 2000 -> balance " + riya.getBalance());

		boolean paid = riya.withdraw(3000);
		System.out.println("Withdraw 3000 -> " + paid +
		    ", balance " + riya.getBalance());

		boolean rejected = riya.withdraw(10000);
		System.out.println("Withdraw 10000 -> " + rejected +
		    ", balance " + riya.getBalance());

		accounts[1].deposit(1500);
		accounts[2].deposit(500);

		for (Account account : accounts) {
			System.out.println(account.getAccountNumber() + " | " +
			    account.getOwnerName() + " | " +
			    account.getBalance() + " | active=" +
			    account.isActive());
		}
		System.out.println();
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
		System.out.println("5. Exit");
		System.out.print("Choose an option: ");
	}

	private static int readChoice(Scanner sc) {

		while (true) {

			if (sc.hasNextInt()) {
				return sc.nextInt();
			}

			// End of input: treat it as EXIT so the loop terminates.
			if (!sc.hasNext()) {
				return EXIT_CHOICE;
			}

			String ignored = sc.next();
			System.out.print(
			    "Not a number (" + ignored + "). Enter 1-" +
			    EXIT_CHOICE + ": ");
		}
	}
}
