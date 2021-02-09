package com.krealll.worklance.model.validator;

public class DescriptionValidator {

    private final static String DESCRIPTION_PATTERN = "[\\p{L}0-9!?%#&*+,.():\\s-_]+";

    private DescriptionValidator(){};

    public static boolean checkDescription(String description){
        boolean result = false;
        if(description!=null&&!description.isEmpty()){
            result = description.matches(DESCRIPTION_PATTERN);
        }
        return result;
    }

}
