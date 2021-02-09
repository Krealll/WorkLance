package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Notification;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.NotificationService;
import com.krealll.worklance.model.service.impl.NotificationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AcceptNotificationCommand implements Command {

    private final static Logger logger = LogManager.getLogger(AcceptNotificationCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        NotificationService notificationService = new NotificationServiceImpl();
        boolean accepted;
        try {
            Long notificationId = Long.parseLong(request.getParameter(RequestParameter.NOTIFICATION_ID));
            Optional<Notification> notification = notificationService.findById(notificationId);
            if(notification.isPresent()){
                accepted = notificationService.acceptNotification(notification.get());
                if(accepted){
                    User user = (User) request.getSession().getAttribute(SessionAttribute.USER);
                    user.setTeam(notification.get().getTeamId());
                    request.getSession().setAttribute(SessionAttribute.USER,user);
                    router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.FIND_TEAM_BY_ID,
                            RequestParameter.TEAM_ID_REDIRECT+notification.get().getTeamId()));
                } else {
                    logger.log(Level.ERROR,"Error during accepting notification");
                    router = new Router(PagePath.NOTIFICATIONS);
                }
            } else {
                logger.log(Level.ERROR,"Error during searching for notification to accept");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
