package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.PaginationParameters;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindEmployeesCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService userService = new UserServiceImpl();
        try {
            List<User> users = new ArrayList<>();
            String keyWord = request.getParameter(RequestParameter.KEY);
            String login = request.getParameter(RequestParameter.LOGIN);
            String specialization = request.getParameter(RequestParameter.CHOOSE);
            if(keyWord!=null){
                users = userService.findByKey(keyWord);
            }
            if(login!=null){
                Optional<User> userOptional = userService.findByLogin(login);
                if(userOptional.isPresent()){
                 users.add(userOptional.get());
                }
            }
            if(specialization!=null){
                users = userService.findBySpecialization(specialization.toUpperCase());
            }
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
        return router;    }
}
