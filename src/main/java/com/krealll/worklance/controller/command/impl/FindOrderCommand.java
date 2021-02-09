package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Order;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.OrderService;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.OrderServiceImpl;
import com.krealll.worklance.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class FindOrderCommand implements Command {

    private final static Logger logger = LogManager.getLogger(FindOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        OrderService service = new OrderServiceImpl();
        UserService userService = new UserServiceImpl();
        try {
            Long orderId = Long.parseLong(request.getParameter(RequestParameter.ORDER_ID));
            Optional<Order> optionalOrder = service.findById(orderId);
            if(optionalOrder.isPresent()){
                request.setAttribute(RequestParameter.CHOSEN_ORDER,optionalOrder.get());
                Optional<User> user = userService.findById(optionalOrder.get().getCustomer());
                if(user.isPresent()){
                    request.setAttribute(RequestParameter.CUSTOMER_ID,user.get().getId());
                    request.setAttribute(RequestParameter.EMAIL_ALLOWED,user.get().getShowEmail());
                    request.setAttribute(RequestParameter.CUSTOMER_EMAIL,user.get().getEmail());
                    router = new Router(PagePath.ORDER);
                }else {
                    logger.log(Level.ERROR,"Bad attempt to finding customer");
                    router = new Router(PagePath.ERROR_500);
                }
            } else {
                logger.log(Level.WARN,"Bad attempt to find order page");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
