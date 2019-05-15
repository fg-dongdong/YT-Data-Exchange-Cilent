package com.example.demo.controller;

import com.example.demo.client.Oauth2StoreServiceAdaptor;
import com.example.demo.request.ClientTokenRequest;
import com.example.demo.response.AccessTokenReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname CallbackController
 * @Description
 * @Date 2019/5/7 10:06
 * @Created by ZLW
 */
@RestController
public class CallbackController {

    @Autowired
    Oauth2StoreServiceAdaptor oauth2StoreServiceAdaptor;
    @Value("localhost.url")
    String localhost;

    @GetMapping("callback")
    public Object getCallbackController(@RequestParam(value = "code",required = false)String code,
                                          @RequestBody(required = false) AccessTokenReq accessTokenReq){
        System.out.println("回调函数code:"+code);
        if(!ObjectUtils.isEmpty(accessTokenReq)){
            System.out.println(accessTokenReq.toString());
            return accessTokenReq;
        }else{
            oauth2StoreServiceAdaptor.getCodeService().addCode(localhost+"/callback",code);
        }
        return code;
    }

}
