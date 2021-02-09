package com.krealll.worklance.model.validator;

public class NameValidator {

    private final static String NAME_PATTERN = "[a-zA-Z][a-zA-Z0-9_]{1,29}";

    private NameValidator(){};

    public static boolean checkName(String name){
        boolean result = false;
        if(name!=null&&!name.isEmpty()){
            result = name.matches(NAME_PATTERN);
        }
        return result;
    }

}
