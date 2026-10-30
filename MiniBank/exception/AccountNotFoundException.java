package exception;

public class AccountNotFoundException extends BankException {

	private final String accountNumber;

	public AccountNotFoundException(String accountNumber) {
		super("Account not found: " + accountNumber);
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
}
