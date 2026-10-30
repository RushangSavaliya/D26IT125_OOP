package model;

public interface WithdrawRule {

	boolean allow(Account account, long amount);
}
