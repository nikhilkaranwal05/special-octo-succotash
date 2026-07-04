# School Management (Java Swing + MySQL)

A minimal, production-ready starter for a School Management desktop app using Java Swing + JDBC (MySQL).  
This starter includes **Student CRUD** (add/update/delete/list) and a clean DAO structure you can extend for Teachers, Classes, Subjects, Attendance, etc.

## Prerequisites
- Java 17+
- Maven 3.8+
- MySQL Server 8+ (running locally)

## 1) Create database user/password
Edit credentials in `src/main/java/com/school/DB.java`:

```java
private static final String USER = "root";     // change if needed
private static final String PASS = "password"; // change if needed
```

## 2) Create database
You have two options:
- **Auto**: The app auto-creates `school_db` and the `students` table on first run.
- **Manual**: Run `src/main/resources/schema.sql` in MySQL Workbench/CLI.

## 3) Build & run
```bash
mvn -v
mvn clean package
java -jar target/school-management-1.0.0.jar
```

If you see connection errors, verify:
- MySQL is running on `localhost:3306`
- Username/password are correct
- The user has privileges to create DBs/tables

## 4) Project structure
```
src/main/java/com/school/
  App.java          -> Main window
  DB.java           -> JDBC connection + auto schema
  Student.java      -> Model
  StudentDAO.java   -> Data access (CRUD)
  StudentPanel.java -> Swing UI (table + form)
src/main/resources/
  schema.sql
pom.xml             -> Maven, includes mysql-connector-j
```

## 5) Extend the system
- Create entities like `Teacher`, `Classroom`, `Subject`, `Enrollment`, `Attendance`.
- Add DAO classes similar to `StudentDAO`.
- Add Swing panels similar to `StudentPanel` and mount them in a `JTabbedPane`.

## 6) Common issues
- **`Communications link failure`**: MySQL not running or port blocked.
- **Timezone warning**: We set `serverTimezone=UTC` in the JDBC URL.
- **Access denied**: Fix username/password and permissions.

Happy building! 🎓
