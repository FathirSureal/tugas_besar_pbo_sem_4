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
        
        // 1. Beritahu browser bahwa data yang dikirim adalah format JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 2. Menangkap teks dari kolom pencarian (dikirim dari index.html)
        String keyword = request.getParameter("search"); 

        // 3. Panggil DAO dan masukkan keyword pencariannya
        LibraryDAO dao = new LibraryDAO();
        List<Komik> daftarKomik = dao.getDaftarKomik(keyword); 

        // 4. Ubah daftar komik Java tersebut menjadi JSON
        Gson gson = new Gson();
        String jsonResult = gson.toJson(daftarKomik);

        // 5. Kirim teks JSON tersebut ke layar browser
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResult);
            out.flush();
        }
    }
}