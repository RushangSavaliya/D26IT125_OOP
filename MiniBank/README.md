# MiniBank

Console banking application built across the OOP lab course (CEUE203).
This is the **Practical 9** stage: `deposit` and `withdraw` are
`synchronized`, so many threads can update one account without losing
updates. The demo first shows the unsynchronized race, then the correct
balance. The Practical 1 menu shell is unchanged.

On start-up the program runs a short demo (model, interfaces, validation,
annotations, exceptions, threads and statements) and then shows the
menu.

## Requirements

- JDK 17 or newer (`javac`, `jar` and `java` on the PATH)

## Compile

```sh
javac -d build $(find exception model util service -name '*.java')
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
| `exception` | `BankException` (checked base), `InsufficientFundsException`, `AccountNotFoundException`, `InvalidAmountException` |
| `util` | `Validator`, `AnnotationValidator`, `CommandParser`, `StatementFormatter`, `AuditTrail` |
| `service` | `MiniBank` (`main`), `AccountWorker` (Runnable depositing into one account) |

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
- `deposit` / `withdraw` — now `throws InvalidAmountException`, and
  `withdraw` also `throws InsufficientFundsException` (with `shortfall`).
- `Account.transfer` — `try`/`catch`/`finally`, re-throwing a `BankException`.
- `AuditTrail` — small `AutoCloseable` used to demonstrate
  try-with-resources.
- `AccountWorker` — `Runnable` that deposits `times` × `amount`; ten
  threads on one `Account` end at exactly 10000 because `deposit` and
  `withdraw` are `synchronized`.

## Menu

| Choice | Option | Status |
| --- | --- | --- |
| 1 | Open Account | placeholder |
| 2 | Deposit | placeholder |
| 3 | Withdraw | placeholder |
| 4 | Transfer | placeholder |
| 5 | Exit | working |
