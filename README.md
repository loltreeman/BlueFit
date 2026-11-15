# BlueFit

Gym membership and subscription tracker for CSCI 180.05 (Spring Boot + Jersey + JPA).

## Running it

1. Start MySQL in the XAMPP control panel and create the database once:
   `CREATE DATABASE bluefit_db;` (Hibernate creates the tables).
2. Make sure `SpringBootDependencies` and `ClientUIDependencies` are in the Eclipse workspace.
3. Run `FakeTwilio` (port 9997) so SMS messages can be sent.
4. Run `app.SpringBootApp` with the Java 8 JRE (port 9999). Test data loads on the first run.
5. Import `BlueFit.postman_collection.json` into Postman. The sample pks assume a fresh database.

Scheduled jobs (expiring subscriptions, renewal reminders, inactivity messages, owner summary)
run every minute for the demo; the SMS messages show up in FakeTwilio's console.
