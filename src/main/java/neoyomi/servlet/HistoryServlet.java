package neoyomi.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import neoyomi.util.DatabaseConnection; // Pakai util terpusat, bukan hardcode

// PERBAIKAN 1: Tambah @WebServlet agar servlet ini terdaftar
// (sebelumnya hanya pakai web.xml tapi entry-nya tidak ada)
@WebServlet(name = "HistoryServlet", urlPatterns = {"/api/history"})
public class HistoryServlet extends HttpServlet {

    // PERBAIKAN 2: Buat tabel otomatis saat servlet pertama kali diinisialisasi
    // sehingga tidak perlu jalankan SQL manual di HeidiSQL
    @Override
    public void init() throws ServletException {
        String createTable = 
            "CREATE TABLE IF NOT EXISTS `reading_history` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `manga_id` VARCHAR(500) NOT NULL, " +
            "  `judul` VARCHAR(255) NOT NULL, " +
            "  `gambar_sampul` TEXT, " +
            "  `tags` VARCHAR(500), " +
            "  `waktu_baca` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "  PRIMARY KEY (`id`), " +
            "  UNIQUE KEY `manga_id` (`manga_id`(255))" + // UNIQUE agar ON DUPLICATE bisa kerja
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                conn.createStatement().execute(createTable);
                System.out.println("✅ Tabel reading_history siap.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Gagal membuat tabel reading_history: " + e.getMessage());
        }
    }

    // GET: Mengambil daftar riwayat untuk ditampilkan di halaman History
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Map<String, String>> historyList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM reading_history ORDER BY waktu_baca DESC";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, String> item = new HashMap<>();
                        item.put("mangaId",      rs.getString("manga_id"));
                        item.put("judul",        rs.getString("judul"));
                        item.put("gambarSampul", rs.getString("gambar_sampul"));
                        item.put("tags",         rs.getString("tags"));
                        item.put("waktuBaca",    rs.getString("waktu_baca"));
                        historyList.add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(historyList));
            out.flush();
        }
    }

    // POST: Menyimpan/update riwayat saat user membuka chapter
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");

        String mangaId     = request.getParameter("mangaId");
        String judul       = request.getParameter("judul");
        String gambarSampul = request.getParameter("gambarSampul");
        String tags        = request.getParameter("tags");

        Map<String, String> jsonResponse = new HashMap<>();

        // Validasi input dasar
        if (mangaId == null || mangaId.trim().isEmpty() ||
            judul == null || judul.trim().isEmpty()) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "mangaId dan judul wajib diisi");
            try (PrintWriter out = response.getWriter()) {
                out.print(new Gson().toJson(jsonResponse));
            }
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                // UPSERT: kalau manga_id sudah ada, update waktu_baca saja
                String sql = 
                    "INSERT INTO reading_history (manga_id, judul, gambar_sampul, tags) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "  judul = VALUES(judul), " +
                    "  gambar_sampul = VALUES(gambar_sampul), " +
                    "  tags = VALUES(tags), " +
                    "  waktu_baca = CURRENT_TIMESTAMP";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, mangaId);
                    stmt.setString(2, judul);
                    stmt.setString(3, gambarSampul);
                    stmt.setString(4, tags);
                    stmt.executeUpdate();
                }
                jsonResponse.put("status", "success");
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Koneksi database gagal");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", e.getMessage());
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(jsonResponse));
            out.flush();
        }
    }
}
