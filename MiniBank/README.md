# MiniBank

Console banking application built across the OOP lab course (CEUE203).
This is the **Practical 1** stage: the application header and menu shell.
Every menu option is a placeholder for a feature added in a later lab.

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
| `MiniBank.java` | `main`, header, menu loop, switch expressions |
| `BankInfo.java` | record holding the bank name and branch |
| `MenuOption.java` | enum of the actions MiniBank offers |

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
