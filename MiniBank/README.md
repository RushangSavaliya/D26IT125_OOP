# MiniBank

Console banking application built across the OOP lab course (CEUE203).
This is the **Practical 6** stage: interfaces, packages and a runnable
JAR. The Practical 1 menu shell is unchanged and every menu option is
still a placeholder for a later lab.

On start-up the program runs a short demo (model behaviour, interface
implementations, validators, command parsing and a formatted statement)
and then shows the menu.

## Requirements

- JDK 17 or newer (`javac`, `jar` and `java` on the PATH)

## Compile

```sh
javac -d build $(find model util service -name '*.java')
```

## Run

```sh
java -cp build service.MiniBank
```

## Build and run the JAR

```sh
jar --create --file minibank.jar --main-class service.MiniBank -C build .
java -jar minibank.jar
```

The manifest inside the JAR contains `Main-Class: service.MiniBank`.

## Packages

| Package | Files |
| --- | --- |
| `model` | `BankInfo`, `MenuOption`, `Customer`, `Account`, `SavingsAccount`, `CurrentAccount`, `FixedDepositAccount`, `TransactionType`, `Command`, `Transactable`, `InterestBearing`, `WithdrawRule`, `Premium` |
| `util` | `Validator`, `CommandParser`, `StatementFormatter` |
| `service` | `MiniBank` (`main`) |

## Key types

- `Transactable` — `deposit` / `withdraw`; implemented by `Account`.
- `InterestBearing` — `interestRate()` plus a default `yearlyInterest()`;
  implemented by `Account`.
- `WithdrawRule` — functional interface `allow(Account, long)`, used once
  as an anonymous class and once as a lambda in `MiniBank`.
- `Premium` — marker interface, implemented by `CurrentAccount`.

## Menu

| Choice | Option | Status |
| --- | --- | --- |
| 1 | Open Account | placeholder |
| 2 | Deposit | placeholder |
| 3 | Withdraw | placeholder |
| 4 | Transfer | placeholder |
| 5 | Exit | working |
