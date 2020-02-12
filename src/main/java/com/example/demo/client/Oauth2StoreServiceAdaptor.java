package com.example.demo.client;

import com.example.demo.utils.CodeService;
import com.example.demo.utils.RefreshTokenService;
import com.example.demo.utils.TokenService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname Oauth2StoreServiceAdaptor
 * @Description
 * @Date 2019/5/8 10:31
 * @Created by ZLW
 */
@Configuration
public class Oauth2StoreServiceAdaptor {
    @Getter
    @Autowired
    CodeService codeService;
    @Getter
    @Autowired
    TokenService tokenService;
    @Getter
    @Autowired
    RefreshTokenService refreshTokenService;

    /**
     * 设置存储实现
     */
    public Oauth2StoreServiceAdaptor codeService(CodeService codeService) {
        this.codeService = codeService;
        return this;
    }

    /**
     * 设置token存储服务
     */
    public Oauth2StoreServiceAdaptor tokenService(TokenService tokenService) {
        this.tokenService = tokenService;
        return this;
    }

    /**
     * 设置refresh存储服务
     */
    public Oauth2StoreServiceAdaptor refreshTokenService(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
        return this;
    }

}
