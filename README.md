# E-Voting System

A production-ready desktop electronic voting application built with **Java Swing** and **MySQL** (via XAMPP).

---

## Prerequisites

| Requirement | Version | Notes |
|---|---|---|
| **JDK** | 17+ | [Download](https://adoptium.net/) |
| **XAMPP** | 8.x+ | MySQL must be running |
| **MySQL Connector/J** | 8.x | [Download JAR](https://dev.mysql.com/downloads/connector/j/) |
| **FlatLaf** (optional) | 3.x | [Download JAR](https://www.formdev.com/flatlaf/) – falls back to Nimbus if absent |

---

## Setup Instructions

### 1. Import the Database

1. Start **XAMPP** and ensure **MySQL** is running.
2. Open **phpMyAdmin** (http://localhost/phpmyadmin).
3. Click **Import** → select `evoting_db.sql` → click **Go**.

Or via command line:
```bash
mysql -u root < evoting_db.sql
```

### 2. Add JDBC JAR to Classpath

Download `mysql-connector-j-8.x.x.jar` and place it in a `lib/` folder at the project root:

```
E-Voting/
├── lib/
│   ├── mysql-connector-j-8.x.x.jar
│   └── flatlaf-3.x.x.jar          (optional)
├── src/
│   └── ...
└── evoting_db.sql
```

### 3. Compile

```bash
# Windows (from E-Voting/ directory)
javac -cp ".;lib/*" -d out -sourcepath src src/main/Main.java

# Linux/macOS
javac -cp ".:lib/*" -d out -sourcepath src src/main/Main.java
```

### 4. Run

```bash
# Windows
java -cp "out;lib/*" main.Main

# Linux/macOS
java -cp "out:lib/*" main.Main
```

---

## First-Time Setup

On the first run, the application will display a **Setup Wizard** to create your initial admin account:

1. **Full Name**: Enter the administrator's name
2. **Email**: Choose your admin email (e.g., admin@myorganization.com)
3. **Password**: Set a secure password (minimum 6 characters)
4. Click **Create Admin Account**

You can now log in with your custom credentials.

### Optional: Use Default Credentials

If you want to use the pre-defined admin account instead:

1. Open `evoting_db.sql`
2. Uncomment the line under "Default admin seed" section:
   ```sql
   INSERT INTO users (full_name, email, password, role, is_approved)
   VALUES ('Administrator', 'admin@evoting.com', SHA2('Admin@123', 256), 'admin', 1);
   ```
3. Re-import the database
4. On first run, skip the Setup Wizard

| Field | Value |
|---|---|
| **Email** | `admin@evoting.com` |
| **Password** | `Admin@123` |

---

## Features

### Admin
- Dashboard with live statistics (elections, voters, votes)
- Manage Elections (CRUD, status changes)
- Manage Candidates (CRUD, photo upload, per-election)
- Manage Voters (approve, reject, delete, bulk approve, search)
- Reports with bar charts and CSV export

### Voter
- Register & await admin approval
- View active elections
- Cast a single vote per election (with confirmation)
- View results of closed elections (bar chart)

---

## Architecture

```
MVC Pattern
├── model/       – POJOs (User, Election, Candidate, Vote, AuditLog)
├── dao/         – Data Access Objects (JDBC + PreparedStatements)
├── controller/  – Business logic (Auth, Election, Vote, Admin)
├── view/        – Java Swing GUI (Login, Register, Admin panels, Voter panels)
├── config/      – Database connection singleton
└── utils/       – Hashing, session, dates, UI theme
```

---

## Security

- SHA-256 password hashing (never stored plain)
- Prepared statements everywhere (SQL injection safe)
- One-vote-per-election enforced at DB (UNIQUE constraint) and application level
- Role-based access control (admin vs voter)
- Full audit logging for all significant actions

---

## Tech Stack

- **Java 17+** with **Swing** GUI
- **FlatLaf** Look & Feel (with Nimbus fallback)
- **MySQL 8.x** via XAMPP
- **JDBC Connector/J 8.x**
- Custom **Java2D** bar charts
