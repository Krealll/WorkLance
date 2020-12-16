package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

public class ToSuccessfulOrderPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePath.SUCCESSFUL_ORDER_CREATION);
        return router;
    }
}
