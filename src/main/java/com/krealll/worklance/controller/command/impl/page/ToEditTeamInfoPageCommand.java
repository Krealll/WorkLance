package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Team;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.TeamService;
import com.krealll.worklance.model.service.impl.TeamServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ToEditTeamInfoPageCommand implements Command {

    private final static Logger logger = LogManager.getLogger(ToEditTeamInfoPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session;
        TeamService service = new TeamServiceImpl();
        try {
            User user = (User)request.getSession().getAttribute(SessionAttribute.USER);
            Long teamId = Long.parseLong(request.getParameter(RequestParameter.TEAM_ID));
            Optional<Team> optionalTeam = service.findById(teamId);
            if(optionalTeam.isPresent()){
                if(optionalTeam.get().getTeamId().equals(user.getTeam())){
                    session = request.getSession();
                    session.setAttribute(SessionAttribute.CHOSEN_TEAM, optionalTeam.get());
                    router = new Router(PagePath.TEAM_EDIT);
                } else {
                    router = new Router(Router.Type.REDIRECT, getRedirectPage(request,
                            CommandType.FIND_USER_BY_ID,RequestParameter.USER_ID_REDIRECT+ user.getId() ));
                }
            } else {
                logger.log(Level.ERROR,"Error during searching for team to accept");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
