package com.krealll.worklance.controller.command;

import com.krealll.worklance.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface Command {

    Router execute(HttpServletRequest request);

    default String getPreviousPage(HttpServletRequest request){
        String previous = (String)request.getSession().getAttribute("previousPage");
        return previous;
    }

    default void addUsersLogin(Map<String,String> parameters, HttpServletRequest request){
        for (int i = 1; i < 19; i++) {
            String temp =  request.getParameter(RequestParameter.TEAM_USER_LOGIN+i+RequestParameter.TEAM_USER_LOGIN_END);
            if(temp==null){
                break;
            }
            parameters.put("user_login_"+i,temp);
        }
    }

}
