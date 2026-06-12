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

import neoyomi.model.ReaderPage;
import neoyomi.scraper.ReaderService;

@WebServlet("/api/read")
public class ReaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String chapterId =
                    request.getParameter("chapterId");

            if (chapterId == null || chapterId.isEmpty()) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                out.print("{\"error\":\"chapterId kosong\"}");

                return;

            }

            ReaderService service =
                    new ReaderService();

            List<ReaderPage> pages =
                    service.ambilHalaman(chapterId);

            out.print(new Gson().toJson(pages));

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(500);

            out.print("{\"error\":\"" + e.getMessage() + "\"}");

        }

    }

}