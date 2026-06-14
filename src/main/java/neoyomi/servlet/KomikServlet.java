package neoyomi.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import neoyomi.dao.LibraryDAO;
import neoyomi.model.Komik;

@WebServlet(name = "KomikServlet", urlPatterns = {"/api/komik"})
public class KomikServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String keyword = request.getParameter("search");
        if (keyword == null) keyword = "";

        // Parameter filter genre (nama genre, atau "all" / kosong untuk semua)
        String genre = request.getParameter("genre");
        if (genre == null) genre = "all";

        // Parameter filter type (manga / manhwa / manhua / comics / all)
        String type = request.getParameter("type");
        if (type == null) type = "all";

        // Sub-endpoint: ?action=genres → kembalikan daftar genre aktif
        String action = request.getParameter("action");

        LibraryDAO dao  = new LibraryDAO();
        Gson       gson = new Gson();

        try (PrintWriter out = response.getWriter()) {

            if ("genres".equals(action)) {
                // Kembalikan daftar genre yang dipakai ekstensi komik
                List<String> genres = dao.getGenreKomikAktif();
                out.print(gson.toJson(genres));
            } else {
                // Default: kembalikan daftar ekstensi dengan filter
                List<Komik> hasil = dao.getDaftarKomikFilter(keyword, genre, type);
                out.print(gson.toJson(hasil));
            }

            out.flush();
        }
    }
}
