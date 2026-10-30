package service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import exception.BankException;
import model.Account;
import model.Command;
import model.SavingsAccount;
import model.TransactionType;
import util.CommandParser;

public class ConcurrencyDemo {

	private ConcurrencyDemo() {
	}

	public static void run() {
		demonstratePool();
		demonstrateProducerConsumer();
		demonstrateDeadlockAndFix();
	}

	private static void demonstratePool() {
		System.out.println("-- TransactionProcessor pool --");

		Account account = new SavingsAccount("Batch", 1000, 0);
		TransactionProcessor processor = new TransactionProcessor();

		for (int i = 0; i < 12; i++) {
			boolean withdraw = i % 4 == 3;

			processor.submit(new TransactionTask(account,
			    withdraw ? TransactionType.WITHDRAW
				: TransactionType.DEPOSIT,
			    withdraw ? 50 : 100));
		}

		processor.stop();
		System.out.println("Batch balance: " +
		    account.getBalance() + " (expected 1750)");
		System.out.println();
	}

	private static void demonstrateProducerConsumer() {
		System.out.println("-- Producer / consumer --");

		Account account = new SavingsAccount("Pipeline", 0, 0);
		TransactionBuffer buffer = new TransactionBuffer(3);
		String number = account.getAccountNumber();

		String[] lines = {
			"DEPOSIT " + number + " 100",
			"WITHDRAW " + number + " 40",
			"DEPOSIT " + number + " 60",
			"WITHDRAW " + number + " 20",
			"DEPOSIT " + number + " 30",
			"WITHDRAW " + number + " 10",
			"DEPOSIT " + number + " 50",
			"WITHDRAW " + number + " 25"
		};

		Thread producer = new Thread(() -> {
			try {
				for (String line : lines) {
					buffer.put(line);
					System.out.println("Produced: " + line);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}, "producer");

		Thread consumer = new Thread(() -> {
			try {
				for (int i = 0; i < lines.length; i++) {
					process(buffer.take(), account);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}, "consumer");

		producer.start();
		consumer.start();
		join(producer);
		join(consumer);

		System.out.println("Pipeline balance: " +
		    account.getBalance() + " (expected 145)");
		System.out.println();
	}

	private static void process(String line, Account account) {
		try {
			Command command = CommandParser.parse(line);

			if (command.type() == TransactionType.DEPOSIT) {
				account.deposit(command.amount());
			} else {
				account.withdraw(command.amount());
			}

			System.out.println("Consumed: " + line +
			    " -> balance " + account.getBalance());
		} catch (IllegalArgumentException | BankException e) {
			System.out.println("Rejected: " + e.getMessage());
		}
	}

	private static void demonstrateDeadlockAndFix() {
		System.out.println("-- Deadlock: opposite lock order --");

		Account a = new SavingsAccount("Deadlock A", 5000, 0);
		Account b = new SavingsAccount("Deadlock B", 5000, 0);

		Thread ab = new Thread(() -> {
			synchronized (a) {
				pause(200);
				synchronized (b) {
					transferBody(a, b, 100);
				}
			}
		}, "deadlock-A-to-B");

		Thread ba = new Thread(() -> {
			synchronized (b) {
				pause(200);
				synchronized (a) {
					transferBody(b, a, 100);
				}
			}
		}, "deadlock-B-to-A");

		// Daemon threads: a detected deadlock must not stop the JVM.
		ab.setDaemon(true);
		ba.setDaemon(true);
		ab.start();
		ba.start();

		pause(600);

		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		long[] deadlocked = bean.findDeadlockedThreads();

		if (deadlocked == null) {
			System.out.println("No deadlock detected this run");
		} else {
			System.out.println("Deadlock detected between:");

			for (ThreadInfo info : bean.getThreadInfo(deadlocked)) {
				System.out.println("  " + info.getThreadName());
			}
		}
		System.out.println();

		System.out.println("-- Deadlock fixed: ordered locks --");

		Account c = new SavingsAccount("Ordered C", 5000, 0);
		Account d = new SavingsAccount("Ordered D", 5000, 0);

		Thread cd = new Thread(() -> orderedTransfer(c, d, 100),
		    "ordered-C-to-D");
		Thread dc = new Thread(() -> orderedTransfer(d, c, 40),
		    "ordered-D-to-C");

		cd.start();
		dc.start();
		join(cd);
		join(dc);

		System.out.println("Ordered transfers finished: " +
		    c.getAccountNumber() + "=" + c.getBalance() + ", " +
		    d.getAccountNumber() + "=" + d.getBalance());
		System.out.println();
	}

	private static void orderedTransfer(Account from, Account to,
	    long amount) {
		Account first = from.getAccountNumber()
		    .compareTo(to.getAccountNumber()) <= 0 ? from : to;
		Account second = first == from ? to : from;

		synchronized (first) {
			synchronized (second) {
				transferBody(from, to, amount);
			}
		}
	}

	private static void transferBody(Account from, Account to,
	    long amount) {
		try {
			from.withdraw(amount);
			to.deposit(amount);
			System.out.println(Thread.currentThread().getName() +
			    " transferred " + amount);
		} catch (BankException e) {
			System.out.println(Thread.currentThread().getName() +
			    " failed: " + e.getMessage());
		}
	}

	private static void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private static void join(Thread thread) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
