package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.service.NotificationService;
import com.krealll.worklance.model.service.impl.NotificationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeclineNotificationCommand implements Command {

    private final static Logger logger = LogManager.getLogger(DeclineNotificationCommand.class);

    @Override
    public Router execute(HttpServletRequest request)  {
        Router router;
        NotificationService notificationService = new NotificationServiceImpl();
        boolean deleted;
        try {
            Long notificationId = Long.parseLong(request.getParameter(RequestParameter.NOTIFICATION_ID));
            deleted = notificationService.delete(notificationId);
            if(deleted){
                router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_NOTIFICATIONS_PAGE,
                        null));
            } else {
                logger.log(Level.ERROR,"Error during declining notification");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
