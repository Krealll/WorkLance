package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.model.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class RegisterCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePath.SUCCESSFUL_SING_UP);
        Map<String,String> params = new HashMap<>();
        params.put("login", request.getParameter(RequestParameter.LOGIN));
        params.put("email", request.getParameter(RequestParameter.EMAIL));
        params.put("password", request.getParameter(RequestParameter.PASSWORD));
        params.put("repeat_password", request.getParameter(RequestParameter.REPEAT_PASSWORD));
        params.put("specialization",request.getParameter(RequestParameter.CHOOSE_SPECIALIZATION));
        HttpSession session = request.getSession();
        session.setAttribute(RequestParameter.ROLE, UserRole.USER);
        return router;
    }
}
