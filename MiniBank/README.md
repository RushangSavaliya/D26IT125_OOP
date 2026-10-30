# MiniBank

Console banking application built across the OOP lab course (CEUE203).
This is the **Practical 7** stage: custom annotations (`@Id`,
`@Positive`, `@MaxLength`) read at run time by a reflective validator.
The Practical 1 menu shell is unchanged and every menu option is still a
placeholder for a later lab.

On start-up the program runs a short demo (model behaviour, interface
implementations, annotation validation, validators, command parsing and a
formatted statement) and then shows the menu.

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
| `model.annotation` | `Id`, `Positive`, `MaxLength` (all `@Target(FIELD)`, `@Retention(RUNTIME)`) |
| `util` | `Validator`, `AnnotationValidator`, `CommandParser`, `StatementFormatter` |
| `service` | `MiniBank` (`main`) |

## Key types

- `Transactable` — `deposit` / `withdraw`; implemented by `Account`.
- `InterestBearing` — `interestRate()` plus a default `yearlyInterest()`;
  implemented by `Account`.
- `WithdrawRule` — functional interface `allow(Account, long)`, used once
  as an anonymous class and once as a lambda in `MiniBank`.
- `Premium` — marker interface, implemented by `CurrentAccount`.
- `@Id` — marks `Account.accountNumber` and `Customer.customerId` for the
  repository in Practical 13.
- `AnnotationValidator` — reads `@Positive` and `@MaxLength` by reflection
  (walking the superclass chain) and returns the error messages.

## Menu

| Choice | Option | Status |
| --- | --- | --- |
| 1 | Open Account | placeholder |
| 2 | Deposit | placeholder |
| 3 | Withdraw | placeholder |
| 4 | Transfer | placeholder |
| 5 | Exit | working |
