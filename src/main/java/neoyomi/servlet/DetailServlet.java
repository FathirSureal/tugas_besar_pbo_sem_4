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

import neoyomi.model.Chapter;
import neoyomi.model.MangaDetail;
import neoyomi.scraper.JikanAPIService;

@WebServlet("/api/detail")
public class DetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // FIX: Tambah header CORS agar tidak ada masalah fetch dari browser
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();

        try {

            String id = request.getParameter("malId");

            if (id == null || id.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"malId kosong\"}");
                return;
            }

            int malId = Integer.parseInt(id.trim());

            // Ambil parameter filter bahasa (default: semua / "all")
            // Nilai: "id", "en", atau "all"
            String lang = request.getParameter("lang");
            if (lang == null || lang.trim().isEmpty()) {
                lang = "all";
            }

            System.out.println("=== DetailServlet ===");
            System.out.println("malId = " + malId);
            System.out.println("lang filter = " + lang);

            // ===========================
            // Ambil detail dari Jikan.
            // Di dalam ambilDetailManga() sudah ada pemanggilan MangaDex
            // untuk mengambil chapter list → TIDAK perlu dipanggil lagi di sini.
            // ===========================

            JikanAPIService jikan = new JikanAPIService();

            // FIX: Teruskan parameter lang agar chapter langsung difilter
            // di sumbernya (MangaDexService), bukan difilter ulang di sini.
            MangaDetail detail = jikan.ambilDetailManga(malId, lang);

            if (detail == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Manga tidak ditemukan\"}");
                return;
            }

            System.out.println("Total Chapter = " + detail.getChapters().size());
            System.out.println("====================");

            // FIX: Tulis JSON ke response — baris ini yang sebelumnya hilang!
            String json = new Gson().toJson(detail);
            out.print(json);
            out.flush();

        } catch (NumberFormatException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"malId harus berupa angka\"}");

        } catch (Exception e) {

            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");

        }
    }
}