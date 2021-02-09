package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ToMembersAddPage implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        request.setAttribute(RequestParameter.NUMBER_OF_MEMBERS,
                session.getAttribute(SessionAttribute.NUMBER_OF_MEMBERS));
        Router router = new Router(PagePath.MEMBERS);
        return router;    }
}
