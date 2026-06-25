package neoyomi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import neoyomi.model.User;
import neoyomi.util.DatabaseConnection;

public class UserDAO {

    // ==========================
    // LOGIN — cek username & password
    // ==========================
    public User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) return null;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("role")
                        );
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ==========================
    // CARI USER BERDASARKAN USERNAME
    // (dipakai untuk cek apakah username sudah dipakai saat register)
    // ==========================
    public User cariByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) return null;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, username);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("role")
                        );
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ==========================
    // REGISTER — selalu jadi role "user" (admin cuma dari seed default)
    // ==========================
    public boolean register(String username, String password) {

        if (cariByUsername(username) != null) {
            return false; // username sudah dipakai
        }

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'user')";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) return false;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // AMBIL SEMUA USER (untuk Panel Admin)
    // ==========================
    public List<User> getSemuaUser() {

        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) return list;

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    list.add(new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================
    // HAPUS USER (khusus admin, tidak bisa hapus akun admin)
    // ==========================
    public boolean hapusUser(int id) {

        String sql = "DELETE FROM users WHERE id = ? AND role != 'admin'";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) return false;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                return rows > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}