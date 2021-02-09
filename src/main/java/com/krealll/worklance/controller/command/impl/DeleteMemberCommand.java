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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class DeleteMemberCommand implements Command {

    private final static Logger logger = LogManager.getLogger(DeleteMemberCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService userService = new UserServiceImpl();
        boolean deleted;
        try {
            User user = (User) request.getSession().getAttribute(SessionAttribute.USER);
            Long userId = Long.parseLong(request.getParameter(RequestParameter.USER_ID));
            Optional<User> userOptional = userService.findById(userId);
            if(userOptional.isPresent()){
                deleted = userService.deleteMember(userOptional.get());
                if(deleted){
                    router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_EDIT_MEMBERS_PAGE,
                            RequestParameter.TEAM_ID_REDIRECT+ user.getTeam().toString()));
                } else {
                    logger.log(Level.ERROR,"Error during deleting team member");
                    router = new Router(PagePath.ERROR_500);
                }
            } else {
                logger.log(Level.WARN,"Error during deleting team member");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
