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
import com.krealll.worklance.model.entity.type.UserRole;
import com.krealll.worklance.model.service.UserAuthenticationService;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.UserAuthenticationServiceImpl;
import com.krealll.worklance.model.service.impl.UserServiceImpl;
import com.krealll.worklance.util.HashGenerator;
import com.krealll.worklance.util.MapKeys;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class RegisterCommand  implements Command  {

    private final static Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = new UserServiceImpl();
        UserAuthenticationService userAuthService = new UserAuthenticationServiceImpl();
        Router router;
        boolean registered;
        Map<String,String> params = new HashMap<>();
        try {
            params.put(MapKeys.LOGIN, request.getParameter(RequestParameter.LOGIN));
            params.put(MapKeys.EMAIL, request.getParameter(RequestParameter.EMAIL));
            params.put(MapKeys.PASSWORD, request.getParameter(RequestParameter.PASSWORD));
            params.put(MapKeys.REPEAT_PASSWORD, request.getParameter(RequestParameter.REPEAT_PASSWORD));
            params.put(MapKeys.SPECIALIZATION,request.getParameter(RequestParameter.CHOOSE_SPECIALIZATION));
            HttpSession session = request.getSession();
            Optional<User> registeredUser = userService.findByLogin(params.get(MapKeys.LOGIN));
            if(!registeredUser.isPresent()){
                registered = userService.registerUser(params);
                if(registered){
                    Optional<User> userOptional = userService.findByLogin(params.get(MapKeys.LOGIN));
                    if(userOptional.isPresent()){
                        User user = userOptional.get();
                        String rememberMe = request.getParameter(RequestParameter.REMEMBER_ME);
                        if(rememberMe!=null){
                            if(rememberMe.equals("true")){
                                if(Boolean.parseBoolean(rememberMe)){
                                    String rawValidator = RandomStringUtils.randomAlphanumeric(HashGenerator.VALIDATOR_LENGTH);
                                    userAuthService.addToken(user.getId(),rawValidator);
                                    Optional<UserAuthenticationToken> token = userAuthService.findByUserId(user.getId());
                                    if(token.isPresent()){
                                        String rawSelector = token.get().getSelector();
                                        session.setAttribute(SessionAttribute.SELECTOR,rawSelector);
                                        session.setAttribute(SessionAttribute.VALIDATOR,rawValidator);
                                        logger.log(Level.INFO,"Token was added");
                                    }
                                }
                            }
                        }
                        session.setAttribute(SessionAttribute.USER,user);
                        router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_HOME_PAGE,
                                null));
                        session.setAttribute(SessionAttribute.ROLE, UserRole.USER);
                        logger.log(Level.INFO,"User was registered");
                    } else {
                        router = new Router(PagePath.ERROR_500);
                        logger.log(Level.ERROR,"Error during registering");
                    }
                } else {
                    router = new Router(Router.Type.REDIRECT, getRedirectPage(request, CommandType.TO_REGISTER_PAGE,
                            null));
                    logger.log(Level.ERROR,"Error during registering");
                }
            } else {
                router = new Router(Router.Type.FORWARD, getRedirectPage(request, CommandType.TO_HOME_PAGE,
                        null));
            }
        } catch (ServiceException  e){
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
