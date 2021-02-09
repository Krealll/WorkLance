package com.krealll.worklance.model.validator;

public class LoginValidator {

    private final static String LOGIN_PATTERN = "[a-zA-Z][a-zA-Z0-9._-]{1,19}";

    private LoginValidator(){};

    public static boolean checkLogin(String login){
        boolean result = false;
        if(login!=null&&!login.isEmpty()){
            result = login.matches(LOGIN_PATTERN);
        }
        return result;
    }

}
