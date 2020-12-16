package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.model.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ToHomePageCommand implements Command {

    private final static String ENGLISH = "en";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        if(session.getAttribute(RequestParameter.LOCALE)==null){
            session.setAttribute(RequestParameter.LOCALE,ENGLISH);
            session.setAttribute(RequestParameter.ROLE, UserRole.GUEST);
        }
        router = new Router(PagePath.HOME);
        return router;
    }
}
