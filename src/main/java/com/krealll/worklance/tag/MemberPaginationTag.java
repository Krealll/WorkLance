package com.krealll.worklance.tag;

import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.model.entity.User;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class MemberPaginationTag extends TagSupport {

    private int pageNumber;
    private int membersOnPage;

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setMembersOnPage(int membersOnPage) {
        this.membersOnPage = membersOnPage;
    }

    @Override
    public int doStartTag() throws JspException {
        ServletRequest request = pageContext.getRequest();
        List<User> users = (List<User>) request.getAttribute(RequestParameter.MEMBERS);
        int start = (pageNumber-1)*membersOnPage;
        int end =Math.min(users.size(), pageNumber*membersOnPage);
        for (int i = start; i < end; i++) {
            User user = users.get(i);
            StringBuilder result = new StringBuilder("<div class=\"row\" style=\"margin-bottom: 20px\">" +
                    "<div class=\"col offset-3\">" +
                    "<a class=\"link\" href=\"projectServlet?user_id=");
            result.append(user.getId());
            result.append("&command=find_user_by_id\"> ");
            result.append(user.getLogin());
            result.append(" </a>"+
                    "</div>" +
                    "<div class=\"col text-left align-self-start\">" +
                    "<button class=\"btn btn-secondary text-left\" type=\"button\" style=\"font-size: 12px;\"" +
                    "onclick=\"location.href='projectServlet?user_id=");
            result.append(user.getId());
            result.append("&command=delete_member'\">" +
                    "X" +
                    "</button>" +
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

