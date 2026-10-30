# MiniBank

Console banking application built across the OOP lab course (CEUE203).
This is the **Practical 3** stage: the `Customer` and `Account` model
classes now print readably, compare by account number, and expose a
nested `Address`; the Practical 1 menu shell is unchanged and every menu
option is still a placeholder for a later lab.

On start-up the program runs a short demo (customer ids, an address and a
clone, deposits, withdrawals, `toString`/`equals`/`hashCode` and an
`instanceof` check) and then shows the menu.

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
| `Account.java` | encapsulated account with `toString`, `equals` and `hashCode` by account number |

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
