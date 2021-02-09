package com.krealll.worklance.controller;

import com.krealll.worklance.controller.command.*;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.model.pool.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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

        handleAttributes(request,router);
        handleCookie(request,response);

        if(router.getType().equals(Router.Type.FORWARD)){
            dispatcher = getServletContext().getRequestDispatcher(router.getPage());
            dispatcher.forward(request,response);
        } else {
            response.sendRedirect((router.getPage()));
        }
    }

    private void handleAttributes(HttpServletRequest request, Router router){
        AttributeHandler handler = new AttributeHandler();
        handler.setAttributes(request);
        request.getSession().setAttribute(SessionAttribute.ATTRIBUTE_HANDLER, handler);
        request.getSession().setAttribute(SessionAttribute.PREVIOUS_PAGE,router.getPage());
    }

    private void handleCookie(HttpServletRequest request, HttpServletResponse response){
        String command = request.getParameter(RequestParameter.COMMAND);
        if(command.equalsIgnoreCase(CommandType.LOGIN.toString())
                ||command.equalsIgnoreCase(CommandType.REGISTER.toString())){
            HttpSession session = request.getSession();
            String selector = (String) session.getAttribute(SessionAttribute.SELECTOR);
            if(selector!=null){
                String validator = (String)session.getAttribute(SessionAttribute.VALIDATOR);
                CookieManager.setTokenCookie(selector,validator,response);
                session.removeAttribute(SessionAttribute.VALIDATOR);
                session.removeAttribute(SessionAttribute.SELECTOR);
            }
        } else if(command.equalsIgnoreCase(CommandType.LOGOUT.toString())){
            CookieManager.removeTokenCookie(response);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
