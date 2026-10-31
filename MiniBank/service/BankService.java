package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidAmountException;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import model.TransactionType;
import util.ReportGenerator;
import util.StatementFormatter;
import util.TransactionLog;
import util.Validator;

public class BankService {

	private static final long SAVINGS_MIN_BALANCE = 1000;
	private static final long CURRENT_OVERDRAFT = 5000;
	private static final Path LOGS_DIR = Path.of("logs");
	private static final Path REPORT_FILE = Path.of("report.txt");

	private final Repository<Account> repository;
	private final Path dataDir;

	public BankService(Repository<Account> repository, Path dataDir) {
		this.repository = repository;
		this.dataDir = dataDir;
	}

	public Account openAccount(String ownerName, String pan, String type,
	    long openingBalance) throws BankException {
		if (ownerName == null || ownerName.isBlank() ||
		    ownerName.contains("|")) {
			throw new BankException("Invalid owner name");
		}

		if (pan == null || !Validator.isValidPan(pan)) {
			throw new BankException("Invalid PAN: " + pan);
		}

		if (openingBalance < 0) {
			throw new InvalidAmountException(
			    "Opening balance cannot be negative: " +
			    openingBalance);
		}

		Account account = switch (type == null ? "" :
		    type.trim().toLowerCase()) {
			case "savings" -> new SavingsAccount(
			    ownerName, openingBalance, SAVINGS_MIN_BALANCE);
			case "current" -> new CurrentAccount(
			    ownerName, openingBalance, CURRENT_OVERDRAFT);
			case "fixed" -> new FixedDepositAccount(
			    ownerName, openingBalance);
			default -> throw new BankException(
			    "Unknown account type: " + type);
		};

		repository.save(account);
		return account;
	}

	public void deposit(String accountNumber, long amount)
	    throws BankException {
		Account account = require(accountNumber);
		account.deposit(amount);
		log("DEPOSIT " + accountNumber + " " + amount);
	}

	public void withdraw(String accountNumber, long amount)
	    throws BankException {
		Account account = require(accountNumber);
		account.withdraw(amount);
		log("WITHDRAW " + accountNumber + " " + amount);
	}

	public void transfer(String fromNumber, String toNumber, long amount)
	    throws BankException {
		if (fromNumber.equals(toNumber)) {
			throw new BankException(
			    "Cannot transfer to the same account");
		}

		Account from = require(fromNumber);
		Account to = require(toNumber);

		from.withdraw(amount);
		to.deposit(amount);
		log("TRANSFER " + fromNumber + " " + toNumber + " " +
		    amount);
	}

	public String statement(String accountNumber)
	    throws BankException {
		return StatementFormatter.buildStatement(
		    require(accountNumber));
	}

	public ArrayList<Account> listAccounts() {
		ArrayList<Account> accounts = repository.findAll();
		Collections.sort(accounts);
		return accounts;
	}

	public void processBatch() {
		ArrayList<Account> accounts = repository.findAll();

		if (accounts.isEmpty()) {
			System.out.println("No accounts available.\n");
			return;
		}

		Collections.sort(accounts);
		TransactionProcessor processor = new TransactionProcessor();

		for (Account account : accounts) {
			processor.register(account);
		}

		Account target = accounts.get(0);
		String number = target.getAccountNumber();
		boolean canWithdraw = target.canWithdraw(200);
		long before = target.getBalance();

		System.out.println("Batch on " + number +
		    ", balance before " + before);

		for (int i = 0; i < 8; i++) {
			boolean withdraw = canWithdraw && i % 2 == 1;
			TransactionType type = withdraw
			    ? TransactionType.WITHDRAW
			    : TransactionType.DEPOSIT;
			long amount = withdraw ? 50 : 100;

			processor.submit(number, type, amount);
			log(type + " " + number + " " + amount);
		}

		processor.stop();
		System.out.println("Batch balance after: " +
		    target.getBalance() + "\n");
	}

	public String runReport() throws IOException {
		if (dataDir == null) {
			throw new IOException("No data directory configured");
		}

		Path logsDir = dataDir.resolve(LOGS_DIR);
		Files.createDirectories(logsDir);

		return ReportGenerator.generate(logsDir,
		    dataDir.resolve(REPORT_FILE));
	}

	private Account require(String accountNumber)
	    throws AccountNotFoundException {
		Account account = repository.findById(accountNumber);

		if (account == null) {
			throw new AccountNotFoundException(accountNumber);
		}

		return account;
	}

	private void log(String line) {
		if (dataDir == null) {
			return;
		}

		try {
			TransactionLog.append(dataDir.resolve(LOGS_DIR)
			    .resolve("transactions.log"), line);
		} catch (IOException e) {
			System.out.println("Could not write log: " +
			    e.getMessage());
		}
	}
}
