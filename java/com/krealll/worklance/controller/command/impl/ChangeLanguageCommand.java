package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.RequestParameter;
import com.krealll.worklance.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {
    private static final String ENGLISH = "en";
    private static final String RUSSIAN = "";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String language = session.getAttribute(RequestParameter.LOCALE).toString();

        if (language.equals(ENGLISH)) {
            session.setAttribute(RequestParameter.LOCALE, RUSSIAN);
        } else {
            session.setAttribute(RequestParameter.LOCALE, ENGLISH);
        }
        return new Router(getPreviousPage(request));
    }
}
