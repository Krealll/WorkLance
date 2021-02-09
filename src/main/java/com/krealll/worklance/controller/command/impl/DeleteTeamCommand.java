package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.entity.type.UserRole;
import com.krealll.worklance.model.service.TeamService;
import com.krealll.worklance.model.service.impl.TeamServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteTeamCommand implements Command {

    private final static Logger logger = LogManager.getLogger(DeleteTeamCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        TeamService teamService = new TeamServiceImpl();
        boolean deleted;
        try {
            Long teamId = Long.parseLong(request.getParameter(RequestParameter.TEAM_ID));

            deleted = teamService.delete(teamId);
            if(deleted){
                User user =(User) request.getSession().getAttribute(SessionAttribute.USER);
                user.setRole(UserRole.USER);
                user.setTeam(null);
                router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_PROFILE_PAGE,
                        null));
            } else {
                logger.log(Level.ERROR,"Error during deleting team");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
