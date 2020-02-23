package com.example.demo.utils;

import com.example.demo.oauth2service.HttpErrorPage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @Classname Http
 * @Description
 * @Date 2019/5/16 15:21
 * @Created by ZLW
 */
public class HttpErrorUtils {

    public static HttpErrorPage getDefaultHttpErrorObject(String errorBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(errorBody, HttpErrorPage.class);
    }
}
