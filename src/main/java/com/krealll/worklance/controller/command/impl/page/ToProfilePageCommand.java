package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ToProfilePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(SessionAttribute.USER);
        request.setAttribute(RequestParameter.CHOSEN_USER,user);
        Router router = new Router(PagePath.EMPLOYEE);
        return router;
    }
}
