package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.UserService;
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

public class EditProfileCommand implements Command {

    private final static Logger logger = LogManager.getLogger(EditProfileCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService userService = new UserServiceImpl();
        HttpSession session = request.getSession();
        boolean updated;
        Map<String,String> params = new HashMap<>();
        try {
            User user = (User) session.getAttribute(SessionAttribute.USER);
            params.put(MapKeys.LOGIN, request.getParameter(RequestParameter.LOGIN));
            params.put(MapKeys.EMAIL, request.getParameter(RequestParameter.EMAIL));
            params.put(MapKeys.SHOW_EMAIL, request.getParameter(RequestParameter.SHOW_EMAIL));
            params.put(MapKeys.SPECIALIZATION, request.getParameter(RequestParameter.CHOOSE_SPECIALIZATION));
            params.put(MapKeys.DESCRIPTION, request.getParameter(RequestParameter.DESCRIPTION));
            params.put(MapKeys.USER_ID,user.getId().toString());
            updated = userService.update(params);
            if(updated){
                Optional<User> optionalUser = userService.findById(user.getId());
                if(optionalUser.isPresent()){
                    router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.FIND_USER_BY_ID,
                            RequestParameter.USER_ID_REDIRECT+optionalUser.get().getId()));
                    session.setAttribute(SessionAttribute.USER,optionalUser.get());
                } else {
                    logger.log(Level.ERROR,"Error during searching for updated user");
                    router = new Router(PagePath.ERROR_500);
                }
            } else {
                logger.log(Level.ERROR,"Error during editing profile");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
