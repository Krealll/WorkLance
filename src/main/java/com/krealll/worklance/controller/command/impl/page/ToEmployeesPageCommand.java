package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.PaginationParameters;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ToEmployeesPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService userService = new UserServiceImpl();
        try {
            List<User> users;
            users = userService.findAll();
            request.setAttribute(RequestParameter.USERS,users);
            request.setAttribute(RequestParameter.ELEMENTS_ON_PAGE, PaginationParameters.EMPLOYEES_ON_PAGE);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER,PaginationParameters.FIRST_PAGE);
            request.setAttribute(RequestParameter.PAGES_AMOUNT,Math.ceil((double)users.size()
                    /PaginationParameters.EMPLOYEES_ON_PAGE));
            request.setAttribute(RequestParameter.NUMBER_OF_EMPLOYEES,users.size());
            router = new Router(PagePath.EMPLOYEES);
        } catch (ServiceException e){
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
