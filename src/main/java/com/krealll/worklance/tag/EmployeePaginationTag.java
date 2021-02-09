package com.krealll.worklance.tag;

import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.model.entity.User;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmployeePaginationTag extends TagSupport {

    private int pageNumber;
    private int employeesOnPage;

    private static final String CONTENT_PATH = "properties/language";
    private final static String RESOURCE_NAME = "specialization.";

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setEmployeesOnPage(int employeesOnPage) {
        this.employeesOnPage = employeesOnPage;
    }

    @Override
    public int doStartTag() throws JspException {
        ServletRequest request = pageContext.getRequest();
        HttpSession session = pageContext.getSession();
        String attrLocale = (String) session.getAttribute(SessionAttribute.LOCALE);
        Locale locale = Locale.forLanguageTag(attrLocale.replace("_", "-"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT_PATH, locale);

        List<User> users = (List<User>) request.getAttribute(RequestParameter.USERS);
        int start = (pageNumber-1)*employeesOnPage;
        int end =Math.min(users.size(),pageNumber*employeesOnPage);
        for (int i = start; i < end; i++) {
            User user = users.get(i);
            StringBuilder result = new StringBuilder("<div class=\"col-12 col-md-6 col-lg-4\" >\n" +
                    "<div class=\"clean-product\" >\n" +
                    "<a href=\"projectServlet?user_id=");
            result.append(user.getId());
            result.append("&command=find_user_by_id\">");
            result.append(user.getLogin());
            result.append("</a>");
            if(user.getShowEmail()){
                result.append("<div>" +
                        "<label>");
                result.append(user.getEmail());
                result.append("</label>" +
                        "</div>");
            }
            result.append("<div>" +
                    "<label>");
            result.append(resourceBundle
                    .getString(RESOURCE_NAME+user.getSpecialization().getName().toLowerCase()));
            result.append("</label>" +
                    "</div>" +
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
