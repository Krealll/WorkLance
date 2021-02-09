package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Team;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.TeamService;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.TeamServiceImpl;
import com.krealll.worklance.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class FindTeamCommand implements Command {

    private final static Logger logger = LogManager.getLogger(FindTeamCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        TeamService teamService = new TeamServiceImpl();
        UserService userService = new UserServiceImpl();
        try {
            Long teamId = Long.parseLong(request.getParameter(RequestParameter.TEAM_ID));
            Optional<Team> teamOptional = teamService.findById(teamId);
            if (teamOptional.isPresent()) {
                List<User> users = userService.findByTeamId(teamOptional.get().getTeamId());
                Optional<User> manager = userService.findManager(teamOptional.get().getTeamId());
                if (manager.isPresent()) {
                    request.setAttribute(RequestParameter.TEAM, teamOptional.get());
                    request.setAttribute(RequestParameter.MANAGER, manager.get());
                    request.setAttribute(RequestParameter.MEMBERS, users);
                    router = new Router(PagePath.TEAM);
                } else {
                    logger.log(Level.ERROR,"Bad attempt to finding manager");
                    router = new Router(PagePath.ERROR_500);
                }
            } else {
                logger.log(Level.WARN,"Bad attempt to find team page");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (
                ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
