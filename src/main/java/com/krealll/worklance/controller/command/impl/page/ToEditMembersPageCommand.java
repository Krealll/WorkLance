package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.command.PaginationParameters;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ToEditMembersPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService userService = new UserServiceImpl();
        List<User> members;
        try {
            Long teamId = Long.parseLong(request.getParameter(RequestParameter.TEAM_ID));
            User user =((User)request.getSession().getAttribute(SessionAttribute.USER));
            Long userTeamId = user.getTeam();
            if(userTeamId.equals(teamId)){
                members = userService.findByTeamId(teamId);
                request.setAttribute(RequestParameter.MEMBERS,members);
                request.setAttribute(RequestParameter.ELEMENTS_ON_PAGE, PaginationParameters.MEMBERS_ON_PAGE);
                request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER,PaginationParameters.FIRST_PAGE);
                request.setAttribute(RequestParameter.PAGES_AMOUNT,
                        Math.ceil((double)members.size()/PaginationParameters.MEMBERS_ON_PAGE));
                request.setAttribute(RequestParameter.NUMBER_OF_MEMBERS,members.size());
                router = new Router( PagePath.TEAM_MEMBERS_EDIT);
            } else {
                router = new Router(Router.Type.REDIRECT, getRedirectPage(request,
                        CommandType.FIND_USER_BY_ID,RequestParameter.USER_ID_REDIRECT+ user.getId() ));
            }
        } catch (ServiceException e) {
            router = new Router( PagePath.ERROR_500);
        }
        return router;
    }
}
