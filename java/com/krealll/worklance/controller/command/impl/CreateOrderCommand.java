package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.RequestParameter;
import com.krealll.worklance.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CreateOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router  = new Router(PagePath.SUCCESSFUL_ORDER_CREATION);
        Map<String,String> orderMap = new HashMap<>();
        orderMap.put("name", request.getParameter(RequestParameter.ORDER_NAME));
        orderMap.put("approxBudget", request.getParameter(RequestParameter.BUDGET));
        return router;
    }
}
