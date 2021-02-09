package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.PaginationParameters;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Team;
import com.krealll.worklance.model.service.TeamService;
import com.krealll.worklance.model.service.impl.TeamServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindTeamsCommand implements Command {


    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        TeamService teamService = new TeamServiceImpl();
        try {
            List<Team> teams = new ArrayList<>();
            String name = request.getParameter(RequestParameter.NAME);
            String manager = request.getParameter(RequestParameter.LOGIN);
            String keyWord = request.getParameter(RequestParameter.KEY);
            if(name!=null){
                Optional<Team> team = teamService.findByName(name);
                if(team.isPresent()){
                    teams.add(team.get());
                }
            }
            if(manager!=null){
                teams = teamService.findByManager(manager);
            }
            if(keyWord!=null){
                teams = teamService.findByKey(keyWord);
            }
            request.setAttribute(RequestParameter.TEAMS,teams);
            request.setAttribute(RequestParameter.ELEMENTS_ON_PAGE, PaginationParameters.TEAMS_ON_PAGE);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER,PaginationParameters.FIRST_PAGE);
            request.setAttribute(RequestParameter.PAGES_AMOUNT,Math.ceil((double)teams.size()
                    /PaginationParameters.TEAMS_ON_PAGE));
            request.setAttribute(RequestParameter.NUMBER_OF_TEAMS,teams.size());
            router = new Router(PagePath.TEAMS);
        } catch (ServiceException e){
            router = new Router(PagePath.ERROR_500);
        }
        return router;    }
}
