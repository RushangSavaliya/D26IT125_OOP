# MiniBank

Console banking application built across the OOP lab course (CEUE203).
This is the **Practical 13** stage: one generic `Repository<T>` stores any
entity type, using the `@Id` field found by reflection as the map key.
The Practical 1 menu shell is unchanged.

On start-up the program runs a short demo (model, interfaces, validation,
annotations, exceptions, threads, concurrency, persistence, reporting,
collections and the generic repository) and then shows the menu.

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
| `util` | `Validator`, `AnnotationValidator`, `CommandParser`, `StatementFormatter`, `AuditTrail`, `StatePersister`, `TransactionLog`, `ReportGenerator` |
| `service` | `MiniBank` (`main`), `AccountWorker`, `TransactionProcessor`, `TransactionTask`, `TransactionBuffer`, `ConcurrencyDemo`, `AccountStore`, `Repository` |

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
- `TransactionProcessor` — fixed pool of 4 threads; `submit` runs tasks,
  `stop` shuts down and awaits termination.
- `TransactionBuffer` — bounded array buffer using `wait`/`notifyAll`.
- `ConcurrencyDemo` — pool batch, producer/consumer, and a deadlock
  (detected with `ThreadMXBean`) followed by the ordered-lock fix.
- `StatePersister` — `ObjectOutputStream`/`ObjectInputStream` save/load of
  `Account[]`; the model classes are `Serializable`.
- `TransactionLog` — append-only `.log` writer using `Files.write`.
- `ReportGenerator` — walks `data/logs/*.log` with `Files.newDirectoryStream`,
  sums deposits/withdrawals, reads size and last-modified attributes, and
  writes `data/report.txt`.
- `AccountStore` — `HashMap<String, Account>` keyed by account number,
  `ArrayList<String>` history, and `sortedByBalance()` using a reversed
  `Comparator.comparingLong`.
- `Account` implements `Comparable<Account>` (by account number).
- `TransactionProcessor` keeps its shared accounts in a `ConcurrentHashMap`
  and can submit tasks by account number.
- `Repository<T>` — generic `HashMap<String, T>` CRUD (`save`, `findById`,
  `findAll`, `delete`), `@SafeVarargs saveAll`, bounded
  `static <N extends Number> double average(N[])`, and a reflective `@Id`
  lookup that walks the superclass chain.

## Menu

| Choice | Option | Status |
| --- | --- | --- |
| 1 | Open Account | placeholder |
| 2 | Deposit | placeholder |
| 3 | Withdraw | placeholder |
| 4 | Transfer | placeholder |
| 5 | Exit | working |
