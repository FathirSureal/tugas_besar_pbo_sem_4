/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package neoyomi.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import neoyomi.dao.LibraryDAO;
import neoyomi.model.Novel;

public class NovelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("search");
        LibraryDAO dao = new LibraryDAO();
        
        // Memanggil data novel dari database
        List<Novel> daftarNovel = dao.getDaftarNovel(keyword);

        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(daftarNovel));
            out.flush();
        }
    }
}