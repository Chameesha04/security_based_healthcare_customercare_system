# Healthcare Customer Care

Spring Boot application for healthcare customer care workflows, appointments, support tickets, medical records, and notifications.

## Requirements

- Java 17
- Microsoft SQL Server

## Configure

Set database connection values in the process environment before starting the app. The defaults target a local SQL Server database named `health` and an empty password; provide `DB_PASSWORD` (and change `DB_USERNAME` as needed) for your own instance. See [.env.example](.env.example) for the supported variables. Do not commit real credentials.

Supported settings are `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `DDL_AUTO`, `SHOW_SQL`, and `SERVER_PORT`.

## Build and run

On Windows:

```powershell
./mvnw.cmd clean verify
./mvnw.cmd spring-boot:run
```

On macOS or Linux:

```sh
./mvnw clean verify
./mvnw spring-boot:run
```

The application listens on port 8080 by default. Database schema management defaults to `update`; set `DDL_AUTO` appropriately for your deployment.

## Tests

Run `./mvnw test` (or `./mvnw.cmd test` on Windows). The application context test requires a reachable SQL Server unless an alternate test datasource is configured.
