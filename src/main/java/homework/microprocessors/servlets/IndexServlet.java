package homework.microprocessors.servlets;

import homework.microprocessors.beans.Microprocessor;
import homework.microprocessors.db.MicroprocessorControl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/index",""})
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Microprocessor> microprocessors = MicroprocessorControl.getAllMicroprocessors();

//        request.getSession().setAttribute("microprocessors", microprocessors);
//        response.sendRedirect(request.getContextPath() + "/index.jsp");

        request.setAttribute("microprocessors", microprocessors);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String microprocessorId = request.getParameter("index");
        System.out.println(microprocessorId);

//        request.getSession().setAttribute("microprocessors", microprocessors);
//        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }
}
