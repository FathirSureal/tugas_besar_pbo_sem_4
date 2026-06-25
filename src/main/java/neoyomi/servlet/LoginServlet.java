package neoyomi.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import neoyomi.dao.UserDAO;
import neoyomi.model.User;
import neoyomi.util.DatabaseConnection;

@WebServlet(name = "LoginServlet", urlPatterns = {"/api/login"})
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    // Buat tabel users otomatis + seed 1 akun admin default
    @Override
    public void init() throws ServletException {

        String createTable =
            "CREATE TABLE IF NOT EXISTS `users` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `username` VARCHAR(100) NOT NULL UNIQUE, " +
            "  `password` VARCHAR(255) NOT NULL, " +
            "  `role` ENUM('user','admin') NOT NULL DEFAULT 'user', " +
            "  `dibuat_pada` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "  PRIMARY KEY (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

        // Akun admin default: username "admin", password "admin123"
        String seedAdmin =
            "INSERT IGNORE INTO users (username, password, role) " +
            "VALUES ('admin', 'admin123', 'admin')";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                conn.createStatement().execute(createTable);
                conn.createStatement().execute(seedAdmin);
                System.out.println("✅ Tabel users siap. Akun admin default: admin / admin123");
            }
        } catch (SQLException e) {
            System.err.println("❌ Gagal membuat tabel users: " + e.getMessage());
        }
    }

    // GET: cek status login saat ini (dipakai index.html & login.html)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Map<String, Object> hasil = new HashMap<>();

        if (session != null && session.getAttribute("username") != null) {
            hasil.put("loggedIn", true);
            hasil.put("username", session.getAttribute("username"));
            hasil.put("role", session.getAttribute("role"));
        } else {
            hasil.put("loggedIn", false);
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(hasil));
        }
    }

    // POST: login / register / logout (dibedakan lewat parameter "action")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");

        String action   = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Map<String, Object> hasil = new HashMap<>();

        if ("logout".equals(action)) {

            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
            hasil.put("status", "success");

        } else if ("register".equals(action)) {

            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

                hasil.put("status", "error");
                hasil.put("message", "Username dan password wajib diisi");

            } else {

                boolean berhasil = userDAO.register(username.trim(), password);

                if (berhasil) {
                    hasil.put("status", "success");
                } else {
                    hasil.put("status", "error");
                    hasil.put("message", "Username sudah dipakai, coba yang lain");
                }
            }

        } else { // default: login

            User user = userDAO.login(username, password);

            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());

                hasil.put("status", "success");
                hasil.put("role", user.getRole());
                hasil.put("username", user.getUsername());
            } else {
                hasil.put("status", "error");
                hasil.put("message", "Username atau password salah");
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(hasil));
        }
    }
}