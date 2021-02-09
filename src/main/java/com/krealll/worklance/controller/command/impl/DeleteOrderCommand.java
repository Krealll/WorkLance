package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.service.OrderService;
import com.krealll.worklance.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteOrderCommand implements Command {

    private final static Logger logger = LogManager.getLogger(DeleteOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        OrderService orderService = new OrderServiceImpl();
        boolean deleted;
        try {
            Long orderId = Long.parseLong(request.getParameter(RequestParameter.ORDER_ID));

            deleted = orderService.delete(orderId);
            if(deleted){
                router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_USER_ORDERS_PAGE,
                        null));
            } else {
                logger.log(Level.ERROR,"Error during deleting order");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
