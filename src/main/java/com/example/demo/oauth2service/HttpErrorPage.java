package com.example.demo.oauth2service;

import lombok.Data;

import java.util.Date;

/**
 * @Classname HttpErrorPage
 * @Description
 * @Date 2019/5/16 15:06
 * @Created by ZLW
 */
@Data
public class HttpErrorPage {

    private Date timestamp;

    private int status;

    private String error;

    private String message;

    private String path;
}
