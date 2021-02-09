package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.NotificationService;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.NotificationServiceImpl;
import com.krealll.worklance.model.service.impl.UserServiceImpl;
import com.krealll.worklance.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InviteUserCommand implements Command {

    private final static Logger logger = LogManager.getLogger(InviteUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        Map<String,String> membersParametersMap = new HashMap<>();
        NotificationService notificationService = new NotificationServiceImpl();
        UserService userService = new UserServiceImpl();
        boolean invited;
        try {
            User user =(User) request.getSession().getAttribute(SessionAttribute.USER);
            Optional<User> invitedUser = userService.findByLogin(request.getParameter(RequestParameter.LOGIN));
            if(invitedUser.isPresent()){
                if(!invitedUser.get().getId().equals(user.getId())){
                    membersParametersMap.put(MapKeys.USER_ID, invitedUser.get().getId().toString());
                    membersParametersMap.put(MapKeys.TEAM_ID, user.getTeam().toString());
                    invited = notificationService.addNotification(membersParametersMap);
                    if(invited){
                        router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_EDIT_MEMBERS_PAGE,
                                RequestParameter.TEAM_ID_REDIRECT+ user.getTeam().toString()));
                    } else {
                        logger.log(Level.ERROR,"Error during inviting user");
                        router = new Router(PagePath.ERROR_500);
                    }
                } else {
                    router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_EDIT_MEMBERS_PAGE,
                            RequestParameter.TEAM_ID_REDIRECT+ user.getTeam().toString()));
                }
            } else {
                router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_EDIT_MEMBERS_PAGE,
                        RequestParameter.TEAM_ID_REDIRECT+ user.getTeam().toString()));
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
