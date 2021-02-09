package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class FindUserCommand implements Command {

    private final static Logger logger = LogManager.getLogger(FindUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService userService = new UserServiceImpl();
        Long userId = Long.parseLong(request.getParameter(RequestParameter.USER_ID));
        try {
            Optional<User> user = userService.findById(userId);
            if(user.isPresent()){
                request.setAttribute(RequestParameter.CHOSEN_USER,user.get());
                router = new Router(PagePath.EMPLOYEE);
            }else {
                logger.log(Level.WARN,"Bad attempt to find user page");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
