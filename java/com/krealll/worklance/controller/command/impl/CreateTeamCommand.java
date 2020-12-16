package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.RequestParameter;
import com.krealll.worklance.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CreateTeamCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePath.SUCCESSFUL_TEAM_CREATION);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("team_name",request.getParameter(RequestParameter.TEAM_NAME));
        addUsersLogin(parameters,request);
        return router;
    }
}
