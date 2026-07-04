package com.school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private static final String URL= "jdbc:mysql://localhost:3306/school_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";     // TODO: change if needed
    private static final String PASS = "12345"; // TODO: change if needed

    static {
        // Ensure database and table exist on startup (best-effort)
        try {
            // Create DB if not exists (connect to MySQL server default 'mysql' db first)
            try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", USER, PASS);
                 Statement s = c.createStatement()) {
                s.executeUpdate("CREATE DATABASE IF NOT EXISTS school_db");
            }
            // Create tables
            try (Connection c2 = getConnection();
                 Statement s2 = c2.createStatement()) {
                s2.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS students (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(120) UNIQUE,
                        phone VARCHAR(20),
                        standard VARCHAR(20),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
