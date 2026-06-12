package neoyomi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Pastikan URL-nya menggunakan nama database-mu yang benar (neuyomi atau neoyomi)
private static final String URL =
    "jdbc:mysql://localhost:3306/neuyomi?useSSL=false&serverTimezone=UTC";
private static final String USER = "root"; 
    private static final String PASSWORD = ""; 
    
    // Method ini sekarang akan selalu membuat koneksi baru setiap kali dipanggil,
    // sehingga tidak akan ada masalah "Pintu Terkunci" lagi.
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
        return conn; // Mengembalikan koneksi yang segar
    }
}