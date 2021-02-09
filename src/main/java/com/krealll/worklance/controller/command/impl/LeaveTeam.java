package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
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
import javax.servlet.http.HttpSession;

public class LeaveTeam implements Command {

    private final static Logger logger = LogManager.getLogger(LeaveTeam.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService userService = new UserServiceImpl();
        HttpSession session = request.getSession();
        boolean leaved;
        try {
            User user = (User) request.getSession().getAttribute(SessionAttribute.USER);
            leaved = userService.leaveTeam(user.getId());
            if(leaved){
                user.setTeam(null);
                router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.FIND_USER_BY_ID,
                        RequestParameter.USER_ID_REDIRECT+user.getId()));
                session.setAttribute(SessionAttribute.USER, user);
            } else {
                logger.log(Level.ERROR,"Error during leaving team");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
