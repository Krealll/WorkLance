package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.entity.UserAuthenticationToken;
import com.krealll.worklance.model.service.UserAuthenticationService;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.UserAuthenticationServiceImpl;
import com.krealll.worklance.model.service.impl.UserServiceImpl;
import com.krealll.worklance.util.HashGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {

    private final static Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        UserService userService = new UserServiceImpl();
        UserAuthenticationService userAuthService = new UserAuthenticationServiceImpl();
        try {
            String login = request.getParameter(RequestParameter.LOGIN);
            String password = request.getParameter(RequestParameter.PASSWORD);
            request.setAttribute(RequestParameter.PASSWORD,password);
            request.setAttribute(RequestParameter.LOGIN,login);
            if(userService.checkLoginAndPassword(login,password)){
                Optional<User> userOptional = userService.findByLogin(login);
                if(userOptional.isPresent()){
                    String rememberMe = request.getParameter(RequestParameter.REMEMBER_ME);
                    if(rememberMe!=null){
                        if(rememberMe.equals("true")){
                            String rawValidator = RandomStringUtils.randomAlphanumeric(HashGenerator.VALIDATOR_LENGTH);
                            userAuthService.addToken(userOptional.get().getId(),rawValidator);
                            Optional<UserAuthenticationToken> token = userAuthService.findByUserId(userOptional.get().getId());
                            if(token.isPresent()){
                                String rawSelector = token.get().getSelector();
                                session.setAttribute(SessionAttribute.SELECTOR,rawSelector);
                                session.setAttribute(SessionAttribute.VALIDATOR,rawValidator);
                                logger.log(Level.INFO,"Token was added");
                            }
                        }
                    }
                    session.setAttribute(SessionAttribute.USER,userOptional.get());
                    router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_HOME_PAGE,
                            null));
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR,"Error during entrance");
                }
            } else {
                logger.log(Level.INFO,"Bad log in attempt");
                request.setAttribute(RequestParameter.SOMETHING_WRONG,RequestParameter.SOMETHING_WRONG);
                router = new Router(PagePath.LOGIN);
            }
        } catch (ServiceException  e){
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
