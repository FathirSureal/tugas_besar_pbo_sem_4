package neoyomi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import neoyomi.model.LibraryItem;
import neoyomi.util.DatabaseConnection;

public class UserLibraryDAO {

    // ── Ambil semua library milik user
    public List<LibraryItem> getLibrary(int userId) {
        List<LibraryItem> list = new ArrayList<>();
        String sql = "SELECT * FROM library WHERE user_id=? ORDER BY created_at DESC";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new LibraryItem(
                        rs.getString("manga_id"),
                        rs.getString("judul"),
                        rs.getString("cover"),
                        rs.getString("content_type")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ── Tambah manga ke library (INSERT IGNORE = tidak error kalau sudah ada)
    public boolean tambahLibrary(int userId, String mangaId,
                                  String judul, String cover, String type) {
        String sql =
            "INSERT IGNORE INTO library(user_id,manga_id,judul,cover,content_type) " +
            "VALUES(?,?,?,?,?)";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, userId);
            ps.setString(2, mangaId);
            ps.setString(3, judul);
            ps.setString(4, cover);
            ps.setString(5, type);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ── Hapus manga dari library
    public boolean hapusLibrary(int userId, String mangaId) {
        String sql = "DELETE FROM library WHERE user_id=? AND manga_id=?";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, userId);
            ps.setString(2, mangaId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ── Cek apakah manga sudah ada di library
    public boolean sudahAda(int userId, String mangaId) {
        String sql = "SELECT 1 FROM library WHERE user_id=? AND manga_id=?";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, userId);
            ps.setString(2, mangaId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}