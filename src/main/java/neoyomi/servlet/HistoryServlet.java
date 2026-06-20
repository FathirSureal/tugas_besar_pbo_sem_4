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
import javax.servlet.http.HttpSession;
import neoyomi.util.DatabaseConnection;

@WebServlet(name = "HistoryServlet", urlPatterns = {"/api/history"})
public class HistoryServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        String createTable =
            "CREATE TABLE IF NOT EXISTS `reading_history` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `manga_id` VARCHAR(500) NOT NULL, " +
            "  `user_id` INT NOT NULL DEFAULT 0, " +
            "  `judul` VARCHAR(255) NOT NULL, " +
            "  `gambar_sampul` TEXT, " +
            "  `tags` VARCHAR(500), " +
            "  `waktu_baca` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "  PRIMARY KEY (`id`), " +
            "  UNIQUE KEY `manga_user` (`manga_id`(255), `user_id`)" +
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

    // Helper: ambil userId dari session, return 0 kalau belum login
    private int getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) return 0;
        return (int) session.getAttribute("userId");
    }

    // GET: Ambil history milik user yang sedang login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int userId = getUserId(request);
        List<Map<String, String>> historyList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM reading_history WHERE user_id = ? ORDER BY waktu_baca DESC";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, userId);
                    try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(historyList));
            out.flush();
        }
    }

    // POST: Simpan, hapusSatu, hapusSemua
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");

        int userId = getUserId(request);
        String action = request.getParameter("action");

        // Hapus semua history milik user ini
        if ("hapusSemua".equals(action)) {
            Map<String, String> hasil = new HashMap<>();
            try (Connection conn = DatabaseConnection.getConnection()) {
                if (conn != null) {
                    String sql = "DELETE FROM reading_history WHERE user_id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, userId);
                        stmt.executeUpdate();
                    }
                    hasil.put("status", "success");
                } else {
                    hasil.put("status", "error");
                    hasil.put("message", "Koneksi database gagal");
                }
            } catch (Exception e) {
                hasil.put("status", "error");
                hasil.put("message", e.getMessage());
            }
            try (PrintWriter out = response.getWriter()) {
                out.print(new Gson().toJson(hasil));
            }
            return;
        }

        // Hapus satu history milik user ini
        if ("hapusSatu".equals(action)) {
            String mangaId = request.getParameter("mangaId");
            Map<String, String> hasil = new HashMap<>();
            try (Connection conn = DatabaseConnection.getConnection()) {
                if (conn != null) {
                    String sql = "DELETE FROM reading_history WHERE manga_id = ? AND user_id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, mangaId);
                        stmt.setInt(2, userId);
                        stmt.executeUpdate();
                    }
                    hasil.put("status", "success");
                } else {
                    hasil.put("status", "error");
                    hasil.put("message", "Koneksi database gagal");
                }
            } catch (Exception e) {
                hasil.put("status", "error");
                hasil.put("message", e.getMessage());
            }
            try (PrintWriter out = response.getWriter()) {
                out.print(new Gson().toJson(hasil));
            }
            return;
        }

        // Default: simpan/update history
        String mangaId      = request.getParameter("mangaId");
        String judul        = request.getParameter("judul");
        String gambarSampul = request.getParameter("gambarSampul");
        String tags         = request.getParameter("tags");

        Map<String, String> jsonResponse = new HashMap<>();

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
                String sql =
                    "INSERT INTO reading_history (manga_id, user_id, judul, gambar_sampul, tags) " +
                    "VALUES (?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "  judul = VALUES(judul), " +
                    "  gambar_sampul = VALUES(gambar_sampul), " +
                    "  tags = VALUES(tags), " +
                    "  waktu_baca = CURRENT_TIMESTAMP";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, mangaId);
                    stmt.setInt(2, userId);
                    stmt.setString(3, judul);
                    stmt.setString(4, gambarSampul);
                    stmt.setString(5, tags);
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
