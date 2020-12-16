package com.krealll.worklance.controller;

import com.krealll.worklance.controller.attributes.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandFactory;
import com.krealll.worklance.controller.router.Router;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/projectServlet")
public class ProjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Router router;
        RequestDispatcher dispatcher;
        Command command = CommandFactory.defineCommand(request);
        router = command.execute(request);
        if(router.getType().equals(Router.Type.FORWARD)){
            dispatcher = getServletContext().getRequestDispatcher(router.getPage());
            request.getSession().setAttribute(SessionAttribute.PREVIOUS_PAGE,router.getPage());
            dispatcher.forward(request,response);
        } else {
            response.sendRedirect((router.getPage()));;
        }
    }

}
