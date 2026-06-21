/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neoyomi.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import neoyomi.dao.UserDAO;
import neoyomi.model.User;

@WebServlet(name = "AdminServlet", urlPatterns = {"/api/admin"})
public class AdminServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    // Cek apakah session sekarang adalah admin.
    // Kalau bukan admin, langsung kirim 403 dan kembalikan true (artinya: STOP, jangan lanjut).
    private boolean bukanAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (!"admin".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Akses ditolak, halaman ini khusus admin.\"}");
            }
            return true;
        }

        return false;
    }

    // GET: lihat daftar semua user (khusus admin)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (bukanAdmin(request, response)) return;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<User> daftarUser = userDAO.getSemuaUser();

        // Jangan kirim balik password mentah ke frontend
        daftarUser.forEach(u -> u.setPassword(null));

        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(daftarUser));
        }
    }

    // POST: hapus user (action=hapus&id=...) — khusus admin
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (bukanAdmin(request, response)) return;

        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");

        Map<String, Object> hasil = new HashMap<>();
        String idParam = request.getParameter("id");

        try {
            int id = Integer.parseInt(idParam);
            boolean berhasil = userDAO.hapusUser(id);

            if (berhasil) {
                hasil.put("status", "success");
            } else {
                hasil.put("status", "error");
                hasil.put("message", "User tidak ditemukan, atau itu akun admin (tidak bisa dihapus)");
            }

        } catch (Exception e) {
            hasil.put("status", "error");
            hasil.put("message", "ID tidak valid");
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(hasil));
        }
    }
}