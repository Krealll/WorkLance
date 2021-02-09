package com.krealll.worklance.tag;

import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.model.entity.Notification;
import com.krealll.worklance.model.entity.type.UserRole;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class NotificationPaginationTag extends TagSupport {

    private int pageNumber;
    private int notificationsOnPage;
    private UserRole userRole;

    private static final String CONTENT_PATH = "properties/language";
    private final static String ACCEPT_RESOURCE = "notifications.accept";
    private final static String DECLINE_RESOURCE = "notifications.decline";
    private final static String USER_RESOURCE = "notifications.user";
    private final static String INVITE_RESOURCE = "notifications.invite";

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setNotificationsOnPage(int notificationsOnPage) {
        this.notificationsOnPage = notificationsOnPage;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String attrLocale = (String) session.getAttribute(SessionAttribute.LOCALE);
        Locale locale = Locale.forLanguageTag(attrLocale.replace("_", "-"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT_PATH, locale);
        ServletRequest request = pageContext.getRequest();
        List<Notification> notifications = (List<Notification>) request.getAttribute(RequestParameter.NOTIFICATIONS);
        int start = (pageNumber-1)*notificationsOnPage;
        int end =Math.min(notifications.size(), pageNumber*notificationsOnPage);
        for (int i = start; i < end; i++) {
            Notification notification = notifications.get(i);
            StringBuilder result = new StringBuilder("<div class=\"row justify-content-center\">" +
                    "<div class=\"col-md-9\" style=\"margin-top: 20px;\">" +
                    "<div class=\"border rounded-0 border-secondary shadow products\" style=\"margin-bottom: 10px;\">" +
                    "<label>");
            result.append(resourceBundle.getString(USER_RESOURCE));
            result.append("</label>" +
                    "<a class=\"link\" href=\"projectServlet?user_id=");
            result.append(notification.getManagerId());
            result.append("&command=find_user_by_id\"> ");
            result.append(notification.getManagerLogin());
            result.append(" </a>"+
                    "<label>");
            result.append(resourceBundle.getString(INVITE_RESOURCE));
            result.append("</label>"+
                    "<a class=\"link\" href=\"projectServlet?team_id=");
            result.append(notification.getTeamId());
            result.append("&command=find_team_by_id\"> ");
            result.append(notification.getTeamName());
            result.append(" </a>"+
                    "</div>");
            if(userRole.getName().equals(UserRole.USER.getName())){
                result.append("<button class=\"btn btn-primary\" type=\"button\"" +
                        "onclick=\"location.href='projectServlet?notification_id=");
                result.append(notification.getNotificationId());
                result.append("&command=accept_notification'\">");
                result.append(resourceBundle.getString(ACCEPT_RESOURCE));
                result.append("</button>");
            }
            result.append("<button class=\"btn btn-primary\" type=\"button\"" +
                    "onclick=\"location.href='projectServlet?notification_id=");
            result.append(notification.getNotificationId());
            result.append("&command=decline_notification'\" style=\"margin-left: 20px;\">");
            result.append(resourceBundle.getString(DECLINE_RESOURCE));
            result.append("</button>"+
                    "</div>" +
                    "</div>");
            try {
                pageContext.getOut().write(result.toString());
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }

}
