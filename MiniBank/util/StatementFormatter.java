package util;

import model.Account;

public class StatementFormatter {

	private StatementFormatter() {
	}

	public static String buildStatement(Account account) {
		StringBuilder statement = new StringBuilder();

		statement.append("==========================================\n");
		statement.append("            MiniBank Statement\n");
		statement.append("==========================================\n");
		statement.append("Account : ")
		    .append(account.getAccountNumber()).append('\n');
		statement.append("Owner   : ")
		    .append(account.getOwnerName()).append('\n');
		statement.append("Balance : ")
		    .append(account.getBalance()).append('\n');
		statement.append("Status  : ")
		    .append(account.isActive() ? "Active" : "Inactive")
		    .append('\n');
		statement.append("==========================================");

		return statement.toString();
	}
}
