package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Team;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.entity.type.UserRole;
import com.krealll.worklance.model.service.TeamService;
import com.krealll.worklance.model.service.impl.TeamServiceImpl;
import com.krealll.worklance.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CreateTeamCommand implements Command {

    private final static Logger logger = LogManager.getLogger(CreateTeamCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        Map<String, String> parameters = new HashMap<>();
        TeamService teamService = new TeamServiceImpl();
        HttpSession session;
        boolean created;
        try {
            User user = (User)request.getSession().getAttribute(SessionAttribute.USER);
            parameters.put(MapKeys.NAME,request.getParameter(RequestParameter.NAME));
            parameters.put(MapKeys.DESCRIPTION,request.getParameter(RequestParameter.DESCRIPTION));
            parameters.put(MapKeys.USER_ID,user.getId().toString());
            created = teamService.create(parameters);
            if (created){
                Optional<Team> teamOptional = teamService.findByName(request.getParameter(RequestParameter.NAME));
                if (teamOptional.isPresent()){
                    User sessionUser = (User)request.getSession().getAttribute(SessionAttribute.USER);
                    sessionUser.setRole(UserRole.MANAGER);
                    sessionUser.setTeam(teamOptional.get().getTeamId());
                    session= request.getSession();
                    session.setAttribute(SessionAttribute.USER,sessionUser);
                    session.setAttribute(SessionAttribute.NUMBER_OF_MEMBERS,request.getParameter(RequestParameter.NUMBER_OF_MEMBERS));
                    router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_MEMBERS_ADD_PAGE,
                            null));
                } else {
                    logger.log(Level.ERROR,"Error during searching for created team");
                    router = new Router(PagePath.ERROR_500);
                }
            } else {
                logger.log(Level.ERROR,"Error during creating team");
                router = new Router(PagePath.TEAM_CREATION);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
