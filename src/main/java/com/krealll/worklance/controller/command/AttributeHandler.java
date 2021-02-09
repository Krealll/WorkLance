package com.krealll.worklance.controller.command;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AttributeHandler {

    private final Map<String,Object> attributes = new HashMap<>();

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HttpServletRequest request){
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String attributeKey = attributeNames.nextElement();
            Object attribute = request.getAttribute(attributeKey);
            attributes.put(attributeKey,attribute);
        }

    }

}
