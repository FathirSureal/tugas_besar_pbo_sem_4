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
import neoyomi.dao.UserLibraryDAO;
import neoyomi.model.LibraryItem;

@WebServlet("/api/library")
public class LibraryServlet extends HttpServlet {

    private final UserLibraryDAO dao = new UserLibraryDAO();

    // ── GET: ambil semua library milik user
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
        List<LibraryItem> list = dao.getLibrary(userId);
        out.print(new Gson().toJson(list));
    }

    // ── POST: tambah manga ke library
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

        // Cek apakah ini request DELETE via POST (method override)
        String method = request.getParameter("_method");
        if ("DELETE".equalsIgnoreCase(method)) {
            hapusDariLibrary(request, response, out, userId);
            return;
        }

        String mangaId = request.getParameter("mangaId");
        String judul   = request.getParameter("judul");
        String cover   = request.getParameter("cover");
        String type    = request.getParameter("type");

        if (mangaId == null || mangaId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"mangaId kosong\"}");
            return;
        }

        boolean berhasil = dao.tambahLibrary(userId, mangaId, judul, cover, type);

        Map<String, Object> hasil = new HashMap<>();
        hasil.put("success", berhasil);
        hasil.put("message", berhasil ? "Berhasil ditambahkan ke library" : "Sudah ada di library");
        out.print(new Gson().toJson(hasil));
    }

    // ── Hapus manga dari library
    private void hapusDariLibrary(HttpServletRequest request,
                                   HttpServletResponse response,
                                   PrintWriter out,
                                   int userId) throws IOException {
        String mangaId = request.getParameter("mangaId");

        if (mangaId == null || mangaId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"mangaId kosong\"}");
            return;
        }

        boolean berhasil = dao.hapusLibrary(userId, mangaId);

        Map<String, Object> hasil = new HashMap<>();
        hasil.put("success", berhasil);
        hasil.put("message", berhasil ? "Dihapus dari library" : "Manga tidak ditemukan");
        out.print(new Gson().toJson(hasil));
    }
}
