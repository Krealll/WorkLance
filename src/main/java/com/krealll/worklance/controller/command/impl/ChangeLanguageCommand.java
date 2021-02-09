package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.AttributeHandler;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class ChangeLanguageCommand implements Command {

    private static final String ENGLISH = "en_EN";
    private static final String RUSSIAN = "ru_RU";

    @Override
    public Router execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String language = session.getAttribute(SessionAttribute.LOCALE).toString();
        if (language.equals(ENGLISH)) {
            session.setAttribute(SessionAttribute.LOCALE, RUSSIAN);
        } else {
            session.setAttribute(SessionAttribute.LOCALE, ENGLISH);
        }
        AttributeHandler handler =
                (AttributeHandler) session.getAttribute(SessionAttribute.ATTRIBUTE_HANDLER);
        Map<String, Object> attributes = handler.getAttributes();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        return new Router(Router.Type.FORWARD,(String)session.getAttribute(SessionAttribute.PREVIOUS_PAGE));
    }
}
