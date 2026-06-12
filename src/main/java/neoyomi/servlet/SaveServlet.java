/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package neoyomi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import neoyomi.dao.LibraryDAO;

public class SaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        // Menangkap data yang dikirim dari tombol Save di Frontend
        String id = request.getParameter("id");
        String judul = request.getParameter("judul");
        String type = request.getParameter("type");
        String tagsParam = request.getParameter("tags"); // Contoh isi: "Action,Comedy,Drama"
        
        // Memecah teks tag menjadi sebuah List
        List<String> tags = null;
        if (tagsParam != null && !tagsParam.isEmpty()) {
            tags = Arrays.asList(tagsParam.split(","));
        }
        
        // Simpan ke Database
        LibraryDAO dao = new LibraryDAO();
        boolean success = dao.saveToLibrary(id, judul, type, tags);
        
        // Kirim balasan ke browser
        try (PrintWriter out = response.getWriter()) {
            if (success) {
                out.print("{\"status\":\"success\"}");
            } else {
                out.print("{\"status\":\"error\"}");
            }
            out.flush();
        }
    }
}
