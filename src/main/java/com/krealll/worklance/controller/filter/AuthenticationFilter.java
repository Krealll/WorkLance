package com.krealll.worklance.controller.filter;

import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.CookieManager;
import com.krealll.worklance.exception.HashGeneratorException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.entity.UserAuthenticationToken;
import com.krealll.worklance.model.entity.type.UserRole;
import com.krealll.worklance.model.service.UserAuthenticationService;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.model.service.impl.UserAuthenticationServiceImpl;
import com.krealll.worklance.model.service.impl.UserServiceImpl;
import com.krealll.worklance.util.HashGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationFilter implements Filter {

    private final static Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig){}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        Cookie[] cookies = httpRequest.getCookies();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        boolean isLogged = session !=null && user!=null&&user.getRole()!= UserRole.GUEST;

        try {
            if (!isLogged && cookies != null) {
                String selector = "";
                String rawValidator = "";
                for (Cookie aCookie : cookies) {
                    if (aCookie.getName().equals(CookieManager.SELECTOR)) {
                        selector = aCookie.getValue();
                    } else if (aCookie.getName().equals(CookieManager.VALIDATOR)) {
                        rawValidator = aCookie.getValue();
                    }
                }
                if(!selector.isEmpty()&&!rawValidator.isEmpty()){
                    UserAuthenticationService userAuthService = new UserAuthenticationServiceImpl();

                    Optional<UserAuthenticationToken> token = userAuthService.findBySelector(selector);
                    if(token.isPresent()){
                        String storedValidator = token.get().getValidator();
                        String hashedCookieValidator = HashGenerator
                                .generate(rawValidator,HashGenerator.TOKEN_ENCRYPTION,HashGenerator.VALIDATOR_LENGTH);
                        if(hashedCookieValidator.equals(storedValidator)){
                            UserService userService = new UserServiceImpl();
                            Optional<User> userOptional = userService.findById(token.get().getUserId());
                            if(userOptional.isPresent()){
                                session.setAttribute(SessionAttribute.USER, userOptional.get());
                                String newSelector =
                                        RandomStringUtils.randomAlphanumeric(HashGenerator.SELECTOR_LENGTH);
                                String newValidator =
                                        RandomStringUtils.randomAlphanumeric(HashGenerator.VALIDATOR_LENGTH);
                                String hashedValidator =
                                        HashGenerator.generate(newValidator,HashGenerator.TOKEN_ENCRYPTION,
                                                HashGenerator.VALIDATOR_LENGTH);
                                token.get().setSelector(newSelector);
                                token.get().setValidator(hashedValidator);
                                userAuthService.update(token.get());
                                CookieManager.setTokenCookie(newSelector,newValidator,httpResponse);
                            }
                        }
                    }
                }
            }
        } catch (HashGeneratorException | ServiceException e){
            logger.log(Level.ERROR,"Error during checking authentication token");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {}

}
