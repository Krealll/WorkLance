package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Order;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.OrderService;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.OrderServiceImpl;
import com.krealll.worklance.model.service.impl.UserServiceImpl;
import com.krealll.worklance.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EditOrderCommand implements Command {

    private final static Logger logger = LogManager.getLogger(EditOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        OrderService service = new OrderServiceImpl();
        UserService userService = new UserServiceImpl();
        HttpSession session = request.getSession();
        boolean updated;
        Map<String,String> params = new HashMap<>();
        try {
            Order order =(Order) session.getAttribute(SessionAttribute.CHOSEN_ORDER);
            session.removeAttribute(SessionAttribute.CHOSEN_ORDER);
            params.put(MapKeys.NAME, request.getParameter(RequestParameter.NAME));
            params.put(MapKeys.BUDGET, request.getParameter(RequestParameter.BUDGET));
            params.put(MapKeys.ORDER_ID, order.getId().toString());
            User sessionUser = (User) request.getSession().getAttribute(SessionAttribute.USER);
            params.put(MapKeys.CUSTOMER, sessionUser.getId().toString());
            params.put(MapKeys.STATUS, request.getParameter(RequestParameter.STATUS));
            String showEmail =  request.getParameter(RequestParameter.SHOW_EMAIL);
            params.put(MapKeys.SHOW_EMAIL, showEmail);
            params.put(MapKeys.DESCRIPTION, request.getParameter(RequestParameter.DESCRIPTION));
            updated = service.update(params);
            if(updated){
                sessionUser.setShowEmail(showEmail !=null);
                session.setAttribute(SessionAttribute.USER,sessionUser);
                Optional<Order> optionalOrder= service.findById(order.getId());
                if(optionalOrder.isPresent()){
                    request.setAttribute(RequestParameter.CHOSEN_ORDER,optionalOrder.get());
                    Optional<User> user = userService.findById(order.getCustomer());
                    if(user.isPresent()){
                        request.setAttribute(RequestParameter.EMAIL_ALLOWED,user.get().getShowEmail());
                        request.setAttribute(RequestParameter.CUSTOMER_ID,user.get().getId());
                        request.setAttribute(RequestParameter.CUSTOMER_EMAIL,user.get().getEmail());
                        router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.FIND_ORDER_BY_ID,
                                RequestParameter.ORDER_ID_REDIRECT) + optionalOrder.get().getId());
                    } else {
                        logger.log(Level.ERROR,"Error during searching for customer");
                        router = new Router(PagePath.ERROR_500);
                    }
                } else {
                    logger.log(Level.ERROR,"Error during searching for updated order");
                    router = new Router(PagePath.ERROR_500);
                }
            } else {
                logger.log(Level.ERROR,"Error during editing order");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }

}
