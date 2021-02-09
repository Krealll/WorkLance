package com.krealll.worklance.controller.command;

import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.command.impl.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

    public static Command defineCommand(HttpServletRequest request) {
        Command current ;
        String action = request.getParameter(RequestParameter.COMMAND);
        if (action != null && !action.isEmpty()) {
            try {
                CommandType commandType = CommandType.valueOf(action.toUpperCase());
                current = commandType.getCommand();
            } catch (IllegalArgumentException e) {
                current = new EmptyCommand();
            }
        } else {
            current = new EmptyCommand();
        }
        return current;
    }

    private CommandFactory() {}
}
