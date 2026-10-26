# KartCity Store

KartCity is a Core Java 17 console e-commerce demo using OOP, Java Collections, in-memory repositories, and JUnit 5.

## Build and run

Install a JDK 17+ and Maven. On Windows, you can install Maven with WinGet:

```powershell
winget install --id Apache.Maven -e
```

Close and reopen PowerShell, then verify Maven is available:

```powershell
mvn -version
```

Open PowerShell in the KartCity project directory and run:

```powershell
mvn clean test
mvn package
java -cp target\classes com.ecommerce.Main
```

The `apache-maven\apache-maven-3.9.9\bin\mvn.cmd` command only works if you have separately downloaded and unpacked Maven into that exact project subfolder. It is not included in the project. If `mvn` is still not recognized after installation, add Maven's `bin` folder to `PATH` and reopen PowerShell.

In IntelliJ IDEA, Eclipse, or VS Code, run `com.ecommerce.Main` as a Java application. Maven dependencies are listed in `pom.xml`.

## Demo accounts

- Customer: `aarav@example.com` / `pass1234`
- Customer: `meera@example.com` / `pass1234`
- Demo admin password: `admin1234`

The admin password is a demonstration-only gate, not production-grade security. Payment methods are simulations; do not enter real payment credentials.

## Included sample catalog

Customer, product, and order IDs are generated as `CUST1001`, `PROD2001`, and `ORD3001` respectively, with separate counters. Sample products receive the first product IDs (`PROD2001` onward); sample customer IDs are `CUST1001` and `CUST1002`. Adding customers or products and placing orders continues each sequence automatically. IDs are Strings and are shown by the UI when records are created.

The sample gift card is `KART100`. Sample customers are created only when their email addresses are absent. Use **Customer Menu → Log in** before cart or order operations. IDs and all other data are in memory: after restarting, the sample records are recreated with their initial IDs and counters begin from their starting values again. When a service is created over an existing in-memory repository, its generator checks existing IDs and advances past the highest one.

## Architecture

- `com.ecommerce.model` — customers, products, carts, orders, payments, and related domain entities
- `com.ecommerce.repository` — in-memory storage
- `com.ecommerce.service` — validation and business rules
- `com.ecommerce.payment` — simulated payment strategies
- `com.ecommerce.ui` — console menus and session state
- `com.ecommerce.Main` — shared dependency wiring and startup data initialization

All data is kept in memory and is lost when the application exits. Startup sample data is recreated on the next run.
