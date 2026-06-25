package neoyomi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // [FIXED] Mengubah neuyomi menjadi neoyomi agar sesuai dengan database di phpMyAdmin
    private static final String URL =
        "jdbc:mysql://localhost:3306/neoyomi?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error Koneksi Database: " + e.getMessage());
        }
        return conn;
    }
}