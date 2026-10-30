public class Account {

	private static long accountCounter = 0;

	private final String accountNumber;
	private String ownerName;
	private long balance;
	private boolean active;

	public Account(String ownerName, long openingBalance) {
		this.accountNumber = generateAccountNumber();
		this.ownerName = ownerName;
		this.balance = openingBalance;
		this.active = true;
	}

	public Account(String ownerName) {
		this(ownerName, 0);
	}

	// Copy constructor: same account number, used to build an equal twin
	// of an account so the equals() contract can be demonstrated.
	public Account(Account other) {
		this.accountNumber = other.accountNumber;
		this.ownerName = other.ownerName;
		this.balance = other.balance;
		this.active = other.active;
	}

	private static String generateAccountNumber() {
		accountCounter++;
		return String.format("AC%04d", accountCounter);
	}

	public void deposit(long amount) {
		balance += amount;
	}

	public boolean withdraw(long amount) {
		if (amount > balance) {
			return false;
		}

		balance -= amount;
		return true;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public long getBalance() {
		return balance;
	}

	public boolean isActive() {
		return active;
	}

	public String toString() {
		return accountNumber + " | " + ownerName + " | " + balance;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof Account)) {
			return false;
		}

		Account other = (Account) o;
		return accountNumber.equals(other.accountNumber);
	}

	public int hashCode() {
		return accountNumber.hashCode();
	}
}
