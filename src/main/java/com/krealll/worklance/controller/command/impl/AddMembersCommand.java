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
import com.krealll.worklance.model.service.NotificationService;
import com.krealll.worklance.model.service.TeamService;
import com.krealll.worklance.model.service.impl.NotificationServiceImpl;
import com.krealll.worklance.model.service.impl.TeamServiceImpl;
import com.krealll.worklance.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddMembersCommand implements Command {

    private final static Logger logger = LogManager.getLogger(AddMembersCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        Map<String,String> membersParametersMap = new HashMap<>();
        NotificationService notificationService = new NotificationServiceImpl();
        TeamService service = new TeamServiceImpl();
        boolean added;
        try {
            User user = (User) request.getSession().getAttribute(SessionAttribute.USER);
            Optional<Team> team = service.findByManagerId(user.getId());
            if(team.isPresent()){
                request.getSession().removeAttribute(SessionAttribute.NUMBER_OF_MEMBERS);
                int numberOfMembers = Integer.parseInt(request.getParameter(RequestParameter.NUMBER_OF_MEMBERS));
                for (int i = 1; i <= numberOfMembers; i++) {
                    membersParametersMap.put(MapKeys.MEMBER_INPUT+i,
                            (String)request.getParameter(RequestParameter.MEMBER_INPUT+i));
                }
                membersParametersMap.put(MapKeys.NUMBER_OF_MEMBERS, Integer.toString(numberOfMembers));
                membersParametersMap.put(MapKeys.USER_LOGIN, user.getLogin());
                membersParametersMap.put(MapKeys.TEAM_ID, Long.toString(team.get().getTeamId()));
                added = notificationService.addNotifications(membersParametersMap);
                if ((added)){
                    request.setAttribute(RequestParameter.CHOSEN_USER,
                            request.getSession().getAttribute(SessionAttribute.USER));
                    router = new Router(Router.Type.REDIRECT,getRedirectPage(request, CommandType.FIND_USER_BY_ID,
                            RequestParameter.USER_ID_REDIRECT+user.getId()));
                } else {
                    logger.log(Level.ERROR,"Error during adding members");
                    router = new Router(PagePath.ERROR_500);
                }
            } else {
                logger.log(Level.ERROR,"Error during searching for team to add members");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e){
            router = new Router(PagePath.ERROR_500);
        }
        return router;

    }
}
