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
import neoyomi.model.ScrapedManga;
import neoyomi.scraper.JikanAPIService;
@WebServlet(name = "ScraperServlet", urlPatterns = {"/api/scrape"})
public class ScraperServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("search");

if (keyword == null) {
    keyword = "";
}

String type = request.getParameter("type");

if (type == null) {
    type = "all";
}

String genre = request.getParameter("genre");

if (genre == null) {
    genre = "all";
}
        
        JikanAPIService apiService = new JikanAPIService();
        List<ScrapedManga> hasil =
        apiService.cariKomik(keyword, type, genre);
        
        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(hasil));
            out.flush();
        }
    }
}
