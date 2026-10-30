package service;

import java.util.Scanner;

import model.Account;
import model.BankInfo;
import model.Command;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.MenuOption;
import model.Premium;
import model.SavingsAccount;
import model.WithdrawRule;
import util.CommandParser;
import util.StatementFormatter;

import static util.Validator.*;

public class MiniBank {

	private static final BankInfo BANK =
	    new BankInfo("MiniBank", "Main Branch");
	private static final int EXIT_CHOICE = 5;

	public static void main(String[] args) {

		printHeader();
		Account[] accounts = demonstrateAccounts();
		demonstrateInterfaces(accounts);
		demonstrateValidationLayer(accounts[0]);

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

	private static Account[] demonstrateAccounts() {
		System.out.println("-- Customers --");
		Customer first = new Customer("Riya", "riya@example.com",
		    "9876543210",
		    new Customer.Address("12 MG Road", "Rajkot", "360001"));
		Customer second = new Customer(
		    "Aarav", "aarav@example.com", "9123456780");
		System.out.println(first.getCustomerId() + " | " +
		    first.getName() + " | " + first.getAddress().getCity());
		System.out.println(
		    second.getCustomerId() + " | " + second.getName());

		Customer copy = first.clone();
		System.out.println("clone is a new object: " + (copy != first));
		System.out.println("clone has the same id: " +
		    copy.getCustomerId().equals(first.getCustomerId()));
		System.out.println();

		System.out.println("-- Account demo --");
		Account[] accounts = {
			new SavingsAccount("Riya", 5000, 1000),
			new CurrentAccount("Aarav", 0, 2000),
			new FixedDepositAccount("Meera", 2500)
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

		Account a1 = accounts[0];
		Account a2 = a1.clone();

		System.out.println("-- Accounts (toString) --");
		for (Account account : accounts) {
			System.out.println(account);
		}
		System.out.println("a2 (twin of a1) = " + a2);
		System.out.println();

		System.out.println("-- equals / hashCode / instanceof --");
		System.out.println("a1.equals(a2) same number: " +
		    a1.equals(a2));
		System.out.println("a1.hashCode() == a2.hashCode(): " +
		    (a1.hashCode() == a2.hashCode()));
		System.out.println("a1.equals(accounts[1]): " +
		    a1.equals(accounts[1]));

		Object candidate = accounts[1];
		System.out.println("candidate instanceof Account: " +
		    (candidate instanceof Account));
		System.out.println();

		System.out.println("-- Polymorphism --");
		for (Account account : accounts) {
			System.out.println(account.getClass().getSimpleName() +
			    " " + account.getAccountNumber() + " rate " +
			    account.interestRate() + "%");
		}

		System.out.println("Savings can withdraw 3500? " +
		    accounts[0].canWithdraw(3500));
		System.out.println("Current can withdraw 3000? " +
		    accounts[1].canWithdraw(3000));
		System.out.println("Fixed deposit can withdraw 100? " +
		    accounts[2].canWithdraw(100));

		for (Account account : accounts) {
			if (account instanceof CurrentAccount current) {
				System.out.println(
				    current.getAccountNumber() +
				    " overdraft limit " +
				    current.getOverdraftLimit());
			}
		}
		System.out.println();

		return accounts;
	}

	private static void demonstrateInterfaces(Account[] accounts) {
		System.out.println("-- WithdrawRule: anonymous vs lambda --");

		WithdrawRule anonymousRule = new WithdrawRule() {
			public boolean allow(Account account, long amount) {
				return account.canWithdraw(amount);
			}
		};

		WithdrawRule lambdaRule =
		    (account, amount) -> account.canWithdraw(amount);

		for (Account account : accounts) {
			System.out.println(account.getAccountNumber() +
			    " allow(500): anonymous=" +
			    anonymousRule.allow(account, 500) +
			    ", lambda=" +
			    lambdaRule.allow(account, 500));
		}
		System.out.println();

		System.out.println("-- yearlyInterest() default method --");
		for (Account account : accounts) {
			System.out.println(account.getAccountNumber() +
			    " yearly interest " + account.yearlyInterest());
		}
		System.out.println();

		System.out.println("-- Premium marker --");
		for (Account account : accounts) {
			if (account instanceof Premium) {
				System.out.println(
				    account.getAccountNumber() + " is premium");
			}
		}
		System.out.println();
	}

	private static void demonstrateValidationLayer(Account account) {
		System.out.println("-- Validator --");

		System.out.println("Mobile 9876543210 -> " +
		    isValidMobile("9876543210"));
		System.out.println("Mobile 12345 -> " +
		    isValidMobile("12345"));

		System.out.println("Email riya@example.com -> " +
		    isValidEmail("riya@example.com"));
		System.out.println("Email riya@example -> " +
		    isValidEmail("riya@example"));

		System.out.println("PAN ABCDE1234F -> " +
		    isValidPan("ABCDE1234F"));
		System.out.println("PAN ABCD1234F -> " +
		    isValidPan("ABCD1234F"));

		System.out.println("IFSC HDFC0001234 -> " +
		    isValidIfsc("HDFC0001234"));
		System.out.println("IFSC HDFC1001234 -> " +
		    isValidIfsc("HDFC1001234"));
		System.out.println();

		System.out.println("-- Command parsing --");
		Command command =
		    CommandParser.parse("DEPOSIT AC0001 500");
		System.out.println("type=" + command.type() +
		    ", accountNumber=" + command.accountNumber() +
		    ", amount=" + command.amount());

		try {
			CommandParser.parse("DEPOSIT AC0001");
		} catch (IllegalArgumentException e) {
			System.out.println("Rejected line: " + e.getMessage());
		}
		System.out.println();

		System.out.println("-- Statement --");
		System.out.println(StatementFormatter.buildStatement(account));
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
