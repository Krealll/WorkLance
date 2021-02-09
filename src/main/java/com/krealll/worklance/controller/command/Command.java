package com.krealll.worklance.controller.command;

import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Command.
 */
public interface Command {

    /**
     * Execute router.
     *
     * @param request the request
     * @return the router
     */
    Router execute(HttpServletRequest request);

    /**
     * Gets redirect page.
     *
     * @param request     the request
     * @param commandType the command type
     * @param parameter   the parameter
     * @return the redirect page
     */
    default String getRedirectPage(HttpServletRequest request, CommandType commandType, String parameter) {
        String s = new String(request.getContextPath());
        StringBuilder result = new StringBuilder(request.getContextPath() + request.getRequestURI()+
                RequestParameter.COMMAND_REDIRECT+ commandType.name().toLowerCase());
        if(parameter!=null){
            result.append(parameter);
        }
        return result.toString();
    }

}
