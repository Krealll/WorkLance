package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.model.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePath.HOME);
        HttpSession session = request.getSession();
        session.setAttribute(RequestParameter.ROLE, UserRole.USER);
        session.setAttribute(RequestParameter.LOCALE, session.getAttribute(RequestParameter.LOCALE));
        return router;
    }
}
