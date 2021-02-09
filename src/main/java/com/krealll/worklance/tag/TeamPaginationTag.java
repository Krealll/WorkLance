package com.krealll.worklance.tag;

import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.model.entity.Team;
import com.krealll.worklance.util.DescriptionUtil;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TeamPaginationTag extends TagSupport {

    private int pageNumber;
    private int teamsOnPage;

    private static final String CONTENT_PATH = "properties/language";
    private final static String DESCRIPTION_RESOURCE = "teams.description";

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTeamsOnPage(int teamsOnPage) {
        this.teamsOnPage = teamsOnPage;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String attrLocale = (String) session.getAttribute(SessionAttribute.LOCALE);
        Locale locale = Locale.forLanguageTag(attrLocale.replace("_", "-"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT_PATH, locale);
        ServletRequest request = pageContext.getRequest();
        List<Team> teams = (List<Team>) request.getAttribute(RequestParameter.TEAMS);
        int start = (pageNumber-1)*teamsOnPage;
        int end =Math.min(teams.size(), pageNumber*teamsOnPage);
        for (int i = start; i < end; i++) {
            Team team = teams.get(i);
            StringBuilder result = new StringBuilder("<div class=\"col-12 col-md-6 col-lg-4\">" +
                    "<div class=\"clean-product-item\"  style=\" border-right: 0px; border-bottom: 0px;\">" +
                    "<a href=\"projectServlet?team_id=");
            result.append(team.getTeamId());
            result.append("&command=find_team_by_id\">");
            result.append(team.getName());
            result.append("</a>" +
                    "<div>" +
                    "<div class=\"col\">" +
                    "<label class=\"col-form-label\">");
            result.append(resourceBundle.getString(DESCRIPTION_RESOURCE));
            result.append("<br/>" +
                    "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class=\"col\">" +
                    "<label class=\"col-form-label\">");
            result.append(DescriptionUtil.shortenDescription(team.getDescription()));
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
