# MiniBank

A console banking application built with Java 17+. Accounts are saved to a
plain-text file and reloaded on start-up.

## Build

```sh
javac -d build $(find exception model util service -name '*.java')
```

## Run

```sh
java -cp build service.MiniBank
```

## JAR

```sh
jar --create --file minibank.jar --main-class service.MiniBank -C build .
java -jar minibank.jar
```

A prebuilt `minibank.jar` is included.

## Tests

```sh
mkdir -p lib
curl -L -o lib/junit-platform-console-standalone-1.10.2.jar \
  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar

javac -d build-test -cp build:lib/junit-platform-console-standalone-1.10.2.jar \
  $(find test -name '*.java')

java -jar lib/junit-platform-console-standalone-1.10.2.jar execute \
  --class-path build:build-test --scan-classpath
```

## Menu

```text
1. Open Account
2. Deposit
3. Withdraw
4. Transfer
5. Statement
6. List Accounts
7. Run Batch
8. End-of-day Report
9. Exit
```

## Rules

- PAN format: `ABCDE1234F`
- Account numbers are generated as `AC0001`, `AC0002`, ...
- Savings must keep at least ₹1000
- Current allows an overdraft of up to ₹5000
- Fixed deposit withdrawals are not allowed

## Data

Accounts are saved to `data/accounts.txt` on exit and loaded on start-up.
Logs go to `data/logs/transactions.log` and the report to `data/report.txt`.
Delete the `data/` folder to reset the app.
