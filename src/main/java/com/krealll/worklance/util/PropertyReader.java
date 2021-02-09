package com.krealll.worklance.util;

import com.krealll.worklance.exception.CustomPropertyException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    public Properties read (String path) throws CustomPropertyException {
        ClassLoader classLoader =Thread.currentThread().getContextClassLoader();
        try(InputStream inputStream = classLoader.getResourceAsStream(path)){
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new CustomPropertyException(e);
        }
    }

}

