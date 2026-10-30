# MiniBank

Console banking application built across the OOP lab course (CEUE203).
This is the **Practical 2** stage: the `Customer` and `Account` model
classes, plus the Practical 1 menu shell. Every menu option is still a
placeholder for a feature added in a later lab.

On start-up the program runs a short account demo (customer ids, deposits,
withdrawals and balances) and then shows the menu.

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
| `Customer.java` | customer details with a generated `CUST101`-style id |
| `Account.java` | encapsulated account: balance changes only via deposit/withdraw |

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
