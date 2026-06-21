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
import neoyomi.dao.DownloadedDAO;
import neoyomi.model.LibraryItem;

@WebServlet("/api/downloaded")
public class DownloadedServlet extends HttpServlet {

    private final DownloadedDAO dao = new DownloadedDAO();

    // GET: ambil semua downloaded milik user
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("{\"error\":\"Belum login\"}");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        List<LibraryItem> list = dao.getDownloaded(userId);
        out.print(new Gson().toJson(list));
    }

    // POST: tambah atau hapus downloaded
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("{\"error\":\"Belum login\"}");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        // Support DELETE via POST (_method=DELETE)
        String method = request.getParameter("_method");
        if ("DELETE".equalsIgnoreCase(method)) {
            String mangaId   = request.getParameter("mangaId");
            boolean berhasil = dao.hapusDownloaded(userId, mangaId);
            Map<String, Object> hasil = new HashMap<>();
            hasil.put("success", berhasil);
            hasil.put("message", berhasil ? "Dihapus dari downloaded" : "Tidak ditemukan");
            out.print(new Gson().toJson(hasil));
            return;
        }

        // Tambah ke downloaded
        String mangaId = request.getParameter("mangaId");
        String judul   = request.getParameter("judul");
        String cover   = request.getParameter("cover");

        if (mangaId == null || mangaId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"mangaId kosong\"}");
            return;
        }

        boolean berhasil = dao.tambahDownloaded(userId, mangaId, judul, cover);

        Map<String, Object> hasil = new HashMap<>();
        hasil.put("success", berhasil);
        hasil.put("message", berhasil
                ? "Berhasil ditandai sebagai downloaded"
                : "Sudah ada di downloaded");
        out.print(new Gson().toJson(hasil));
    }
}
