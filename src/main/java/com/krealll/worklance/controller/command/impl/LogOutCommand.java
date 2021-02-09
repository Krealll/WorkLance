package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.command.CookieManager;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.builder.UserBuilder;
import com.krealll.worklance.model.entity.UserAuthenticationToken;
import com.krealll.worklance.model.entity.type.UserRole;
import com.krealll.worklance.model.service.UserAuthenticationService;
import com.krealll.worklance.model.service.impl.UserAuthenticationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LogOutCommand implements Command {

    private final static Logger logger = LogManager.getLogger(LogOutCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(Router.Type.REDIRECT, PagePath.ERROR_500);
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            String selector="";
            for(Cookie cookie: cookies){
                if(cookie.getName().equals(CookieManager.SELECTOR)){
                    selector = cookie.getValue();
                }
            }
            if(!selector.isEmpty()){
                UserAuthenticationService userAuthService = new UserAuthenticationServiceImpl();
                try {
                    Optional<UserAuthenticationToken> token = userAuthService.findBySelector(selector);
                    if(token.isPresent()){
                        boolean result =  userAuthService.delete(token.get());
                        if(!result){
                            logger.log(Level.ERROR,"Error while deleting token");
                        } else {
                            UserBuilder userBuilder = new UserBuilder();
                            userBuilder.setRole(UserRole.GUEST);
                            request.getSession().setAttribute(SessionAttribute.ROLE,userBuilder.build());
                            router = new Router(Router.Type.FORWARD, getRedirectPage(request, CommandType.TO_HOME_PAGE,
                                    null));
                            logger.log(Level.INFO,"Token was deleted");
                        }
                    }
                } catch (ServiceException e) {
                    logger.log(Level.ERROR,"Error during logging out",e);
                    router = new Router(PagePath.ERROR_500);
                }
            }
        }
        request.getSession().invalidate();
        return router;
    }
}
