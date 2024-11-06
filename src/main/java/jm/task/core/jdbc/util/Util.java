package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_driver = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/preproject";
    private static final String DB_USER = "root";
    private static final String DB_password = "root";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DB_driver);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_password);
            System.out.println("Connected to the database successfully");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to database");
        }
        return conn;
    }
}
