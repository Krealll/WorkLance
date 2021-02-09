package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.model.builder.UserBuilder;
import com.krealll.worklance.model.entity.type.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ToHomePageCommand implements Command {

    private final static String ENGLISH = "en_EN";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        if(session.getAttribute(SessionAttribute.LOCALE)==null){
            session.setAttribute(SessionAttribute.LOCALE,ENGLISH);
            UserBuilder userBuilder = new UserBuilder();
            userBuilder.setRole(UserRole.GUEST);
            session.setAttribute(SessionAttribute.USER, userBuilder.build());
        }
        router = new Router(PagePath.HOME);
        return router;
    }
}
