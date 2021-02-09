package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Team;
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

public class EditTeamCommand implements Command {

    private final static Logger logger = LogManager.getLogger(EditTeamCommand.class);

    @Override
    public Router execute(HttpServletRequest request){
        Router router;
        TeamService teamService = new TeamServiceImpl();
        boolean updated;
        HttpSession session;
        Map<String,String> params = new HashMap<>();
        try {
            session = request.getSession();
            Team team = (Team)session.getAttribute(SessionAttribute.CHOSEN_TEAM);
            session.removeAttribute(SessionAttribute.CHOSEN_TEAM);
            params.put(MapKeys.NAME, request.getParameter(RequestParameter.NAME));
            params.put(MapKeys.TEAM_ID, team.getTeamId().toString());
            params.put(MapKeys.DESCRIPTION, request.getParameter(RequestParameter.DESCRIPTION));
            updated = teamService.update(params);
            if(updated){
                router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.FIND_TEAM_BY_ID,
                                RequestParameter.TEAM_ID_REDIRECT+team.getTeamId()));
            } else {
                logger.log(Level.ERROR,"Error during editing team");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
