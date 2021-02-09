package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.PaginationParameters;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Notification;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.NotificationService;
import com.krealll.worklance.model.service.impl.NotificationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ToNotificationsPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        NotificationService notificationService = new NotificationServiceImpl();
        HttpSession session;
        List<Notification> notifications;
        try {
            session = request.getSession();
            User user= (User)session.getAttribute(SessionAttribute.USER);
            notifications = notificationService.findByUserId(user.getId());
            request.setAttribute(RequestParameter.NOTIFICATIONS,notifications);
            request.setAttribute(RequestParameter.ELEMENTS_ON_PAGE,PaginationParameters.NOTIFICATIONS_ON_PAGE);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER,PaginationParameters.FIRST_PAGE);
            request.setAttribute(RequestParameter.PAGES_AMOUNT,
                    Math.ceil((double)notifications.size()/PaginationParameters.NOTIFICATIONS_ON_PAGE));
            request.setAttribute(RequestParameter.NOTIFICATIONS_AMOUNT,notifications.size());
            router = new Router(PagePath.NOTIFICATIONS);
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
