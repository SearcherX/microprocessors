package homework.microprocessors.servlets;

import homework.microprocessors.beans.Microprocessor;
import homework.microprocessors.db.MicroprocessorControl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UpdateServlet", value = "/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int microprocessorId = Integer.parseInt(request.getParameter("id"));
        Microprocessor microprocessor = MicroprocessorControl.getMicroprocessorById(microprocessorId);
        request.setAttribute("microprocessor", microprocessor);
        request.setAttribute("action", "update");
        getServletContext().getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("данные получены");
        int id = Integer.parseInt(request.getParameter("id"));
        String model = request.getParameter("model");
        int dataBitDepth = Integer.parseInt(request.getParameter("dataBitDepth"));
        int addressBitDepth = Integer.parseInt(request.getParameter("addressBitDepth"));
        String clockSpeeds = request.getParameter("clockSpeeds");
        long addressSpaces = Long.parseLong(request.getParameter("addressSpaces"));
        String numberOfCommandsStr = request.getParameter("numberOfCommands");
        Integer numberOfCommands = numberOfCommandsStr.equals("") ? null : Integer.parseInt(numberOfCommandsStr);
        int numberOfElements = Integer.parseInt(request.getParameter("numberOfElements"));
        int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
        Microprocessor microprocessor = new Microprocessor(id, model, dataBitDepth, addressBitDepth, addressSpaces,
                numberOfCommands, numberOfElements, releaseYear);
        microprocessor.setClockSpeeds(clockSpeeds);

        MicroprocessorControl.update(microprocessor);
        response.sendRedirect(request.getContextPath() + "/index");
    }
}
