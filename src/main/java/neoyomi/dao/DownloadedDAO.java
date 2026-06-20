/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neoyomi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import neoyomi.model.LibraryItem;
import neoyomi.util.DatabaseConnection;

public class DownloadedDAO {

    // Ambil semua downloaded milik user
    public List<LibraryItem> getDownloaded(int userId) {
        List<LibraryItem> list = new ArrayList<>();
        String sql = "SELECT * FROM downloaded WHERE user_id=? ORDER BY created_at DESC";
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
                        "downloaded"
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tandai manga sebagai downloaded
    public boolean tambahDownloaded(int userId, String mangaId, String judul, String cover) {
        String sql =
            "INSERT IGNORE INTO downloaded(user_id, manga_id, judul, cover) " +
            "VALUES(?,?,?,?)";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, userId);
            ps.setString(2, mangaId);
            ps.setString(3, judul);
            ps.setString(4, cover);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hapus dari downloaded
    public boolean hapusDownloaded(int userId, String mangaId) {
        String sql = "DELETE FROM downloaded WHERE user_id=? AND manga_id=?";
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

    // Cek apakah sudah ada
    public boolean sudahAda(int userId, String mangaId) {
        String sql = "SELECT 1 FROM downloaded WHERE user_id=? AND manga_id=?";
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
