package com.krealll.worklance.controller.filter;

import com.krealll.worklance.controller.AvailableCommand;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandFactory;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.command.impl.EmptyCommand;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.entity.type.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@WebFilter(urlPatterns = {"/projectServlet"})
public class RoleAccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(SessionAttribute.USER);
        UserRole role = user.getRole();
        String commandName = request.getParameter(RequestParameter.COMMAND);
        Command command = CommandFactory.defineCommand(request);

        if(command.getClass() != EmptyCommand.class){
            Set<CommandType> availableCommands = new HashSet<>();
            if(role.getName().equals(AvailableCommand.GUEST.getName())){
                availableCommands = AvailableCommand.GUEST.getAvailableCommands();
            } else if (role.getName().equals(AvailableCommand.USER.getName())){
                availableCommands = AvailableCommand.USER.getAvailableCommands();
            } else if(role.getName().equals(AvailableCommand.MANAGER.getName())){
                availableCommands = AvailableCommand.MANAGER.getAvailableCommands();
            } else {
                response.sendError(500);
            }
            if(!availableCommands.contains(CommandType.valueOf(commandName.toUpperCase()))){
                response.sendError(403);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {}
}
