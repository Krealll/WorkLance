package com.krealll.worklance.util;

public class DescriptionUtil {

    private final static String DESCRIPTION_END="...";

    public static String shortenDescription(String description){
        StringBuilder stringBuilder = new StringBuilder(description);
        if(stringBuilder.length()>15){
            stringBuilder.delete(15,stringBuilder.length());
            stringBuilder.append(DESCRIPTION_END);
        }
        return  stringBuilder.toString();
    }

}
