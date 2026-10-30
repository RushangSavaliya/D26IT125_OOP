# MiniBank

Console banking application built across the OOP lab course (CEUE203).
This is the **Practical 5** stage: `Account` is abstract and the bank has
three concrete account types (`SavingsAccount`, `CurrentAccount`,
`FixedDepositAccount`) selected at run time through polymorphism. The
Practical 1 menu shell is unchanged and every menu option is still a
placeholder for a later lab.

On start-up the program runs a short demo (model behaviour, validators,
command parsing, interest rates and withdrawal rules per account type,
and a formatted statement) and then shows the menu.

## Requirements

- JDK 17 or newer (`javac` and `java` on the PATH)

## Compile

```sh
javac *.java
```

## Run

```sh
java MiniBank
```

Type a menu number and press Enter. Enter `5` (Exit) to stop.

## Structure

| File | Purpose |
| --- | --- |
| `MiniBank.java` | `main`, header, account demo, menu loop, switch expressions |
| `BankInfo.java` | record holding the bank name and branch |
| `MenuOption.java` | enum of the actions MiniBank offers |
| `Customer.java` | customer details, nested `Address`, `clone()` |
| `Account.java` | abstract base: id, balance, `deposit`/`withdraw`, `toString`, `equals`, `hashCode` |
| `SavingsAccount.java` | 4.0% interest, withdrawal must keep the balance at or above `minBalance` |
| `CurrentAccount.java` | 0% interest, withdrawal may go down to `-overdraftLimit` |
| `FixedDepositAccount.java` | 7.0% interest, withdrawals locked |
| `Validator.java` | regex checks for mobile, email, PAN and IFSC |
| `TransactionType.java` | enum of `DEPOSIT`, `WITHDRAW`, `TRANSFER` |
| `Command.java` | record: type, account number, amount |
| `CommandParser.java` | parses `DEPOSIT AC0001 500` into a `Command` |
| `StatementFormatter.java` | multi-line statement built with `StringBuilder` |

The classes are in the default package for now; Practical 6 moves them
into the `model`, `util` and `service` packages.

## Menu

| Choice | Option | Status |
| --- | --- | --- |
| 1 | Open Account | placeholder |
| 2 | Deposit | placeholder |
| 3 | Withdraw | placeholder |
| 4 | Transfer | placeholder |
| 5 | Exit | working |
