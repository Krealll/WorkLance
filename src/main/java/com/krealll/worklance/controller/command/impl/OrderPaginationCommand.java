package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.AttributeHandler;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class OrderPaginationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        AttributeHandler handler =
                (AttributeHandler) session.getAttribute(SessionAttribute.ATTRIBUTE_HANDLER);
        Map<String, Object> attributes = handler.getAttributes();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        String currentNumber = request.getParameter(RequestParameter.CURRENT_PAGE_NUMBER);
        request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER, currentNumber);
        router = new Router(PagePath.ORDERS);
        return router;
    }
}
