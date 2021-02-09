package com.krealll.worklance.controller.command;

import com.krealll.worklance.model.pool.ConnectionPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

    public final static String SELECTOR = "selector";
    public final static String VALIDATOR = "validator";
    public final static int REMEMBER_ME_TIME_TO_LIVE = 86400;

    private CookieManager() {
    }

    public static void setTokenCookie(String selector, String validator, HttpServletResponse response){
        Cookie cookieSelector = new Cookie(SELECTOR,selector);
        Cookie cookieValidator = new Cookie(VALIDATOR,validator);
        cookieSelector.setMaxAge(REMEMBER_ME_TIME_TO_LIVE);
        cookieValidator.setMaxAge(REMEMBER_ME_TIME_TO_LIVE);
        response.addCookie(cookieSelector);
        response.addCookie(cookieValidator);
    }

    public static void removeTokenCookie(HttpServletResponse response){
        Cookie cookieSelector = new Cookie(SELECTOR,"");
        Cookie cookieValidator = new Cookie(VALIDATOR,"");
        cookieSelector.setMaxAge(0);
        cookieValidator.setMaxAge(0);
        response.addCookie(cookieSelector);
        response.addCookie(cookieValidator);
    }
}
