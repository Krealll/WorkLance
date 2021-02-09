package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
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
import java.util.Optional;

public class ToEditProfilePageCommand implements Command {

    private final static Logger logger = LogManager.getLogger(ToEditProfilePageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService userService = new UserServiceImpl();
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute(SessionAttribute.USER);
            Optional<User> optionalUser = userService.findById(user.getId());
            if(optionalUser.isPresent()){
                session.setAttribute(SessionAttribute.USER,optionalUser.get());
                request.setAttribute(RequestParameter.CHOSEN_USER,optionalUser.get());
                router = new Router(PagePath.PROFILE_EDIT);
            } else {
                logger.log(Level.ERROR,"Error during searching for user to edit");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
