package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
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

public class CreateOrderCommand implements Command {

    private final static Logger logger = LogManager.getLogger(CreateOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        Map<String,String> orderMap = new HashMap<>();
        OrderService orderService = new OrderServiceImpl();
        UserService userService = new UserServiceImpl();
        boolean created;
        try {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute(SessionAttribute.USER);
            orderMap.put(MapKeys.NAME, request.getParameter(RequestParameter.NAME));
            orderMap.put(MapKeys.BUDGET, request.getParameter(RequestParameter.BUDGET));
            orderMap.put(MapKeys.SHOW_EMAIL,request.getParameter(RequestParameter.SHOW_EMAIL));
            orderMap.put(MapKeys.DESCRIPTION, request.getParameter(RequestParameter.DESCRIPTION));
            orderMap.put(MapKeys.USERS_USER_ID, (user).getId().toString());

            created = orderService.create(orderMap);
            if(created){
                Optional<User> userOptional = userService.findById(user.getId());
                if (userOptional.isPresent()){
                    session.setAttribute(SessionAttribute.USER,userOptional.get());
                    request.setAttribute(RequestParameter.CHOSEN_USER,userOptional.get());
                }
                router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_PROFILE_PAGE,
                        null));
            } else {
                logger.log(Level.ERROR,"Error during order creation");
                router = new Router(PagePath.ORDER_CREATION);
            }
        } catch (ServiceException e){
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
