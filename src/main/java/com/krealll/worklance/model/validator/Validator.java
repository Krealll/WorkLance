package com.krealll.worklance.model.validator;

import com.krealll.worklance.util.MapKeys;

import java.util.Map;

public class Validator {

    private Validator(){}

    public static boolean checkLogIn(Map<String,String> parameters){
        boolean result = true;
        String login = parameters.get(MapKeys.LOGIN);
        if(login!=null&&!LoginValidator.checkLogin(login)){
            result = false;
        }
        String password = parameters.get(MapKeys.PASSWORD);
        if(password!=null&&!PasswordValidator.checkPassword(password)){
            result = false;
        }
        return result;
    }

    public static boolean checkRegistration(Map<String,String> parameters){
        boolean result = true;
        String login = parameters.get(MapKeys.LOGIN);
        if(login!=null&&!LoginValidator.checkLogin(login)){
            result = false;
        }
        String email = parameters.get(MapKeys.EMAIL);
        if(email!=null&&!EmailValidator.checkEmail(email)){
            result = false;
        }
        String password = parameters.get(MapKeys.PASSWORD);
        if(password!=null&&!PasswordValidator.checkPassword(password)){
            result = false;
        }
        String repeatPassword = parameters.get(MapKeys.REPEAT_PASSWORD);
        if(repeatPassword!=null&&!PasswordValidator.checkPassword(repeatPassword)){
            result = false;
        }
        if(!repeatPassword.equals(password)){
            result = false;
        }
        return result;
    }

    public static boolean checkEditingProfile(Map<String,String> parameters){
        boolean result = true;
        String login = parameters.get(MapKeys.LOGIN);
        if(login!=null&&!LoginValidator.checkLogin(login)){
            result = false;
        }
        String email = parameters.get(MapKeys.EMAIL);
        if(email!=null&&!EmailValidator.checkEmail(email)){
            result = false;
        }
        String description = parameters.get(MapKeys.DESCRIPTION);
        if(description!=null&&!DescriptionValidator.checkDescription(description)){
            result = false;
        }
        return result;
    }


    public static boolean checkTeam(Map<String,String> parameters){
        boolean result = true;
        String name = parameters.get(MapKeys.NAME);
        if(name!=null&&!NameValidator.checkName(name)){
            result = false;
        }
        String description = parameters.get(MapKeys.DESCRIPTION);
        if(description!=null&&!DescriptionValidator.checkDescription(description)){
            result = false;
        }
        return result;
    }

    public static boolean checkOrder(Map<String,String> parameters){
        boolean result = true;
        String name = parameters.get(MapKeys.NAME);
        if(name!=null&&!NameValidator.checkName(name)){
            result = false;
        }
        String budget = parameters.get(MapKeys.BUDGET);
        if(!BudgetValidator.checkBudget(budget)){
            result = false;
        }
        String description = parameters.get(MapKeys.DESCRIPTION);
        return DescriptionValidator.checkDescription(description);
    }


}
