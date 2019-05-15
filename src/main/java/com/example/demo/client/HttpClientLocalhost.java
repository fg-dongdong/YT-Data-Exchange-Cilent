package com.example.demo.client;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.util.Map;

/**
 * @Classname HttpClientLocalhost
 * @Description
 * @Date 2019/5/7 17:58
 * @Created by ZLW
 */
@Service
@Slf4j
public class HttpClientLocalhost {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    @Autowired
    @Qualifier("httpClientRestTemplate")
    @Setter
    RestTemplate restTemplate;

    private static final String ACCESS_TOKEN_URL = "%s/oauth/token?client_id=%s&grant_type=%s&redirect_uri=%s&client_secret=%s&code=%s";
    private static final String REFRESH_TOKEN_URL = "%s/oauth/token?grant_type=%s&refresh_token=%s&client_id=%s&client_secret=%s";
    private static final String OAUTH_AUTHORIZE_URL = "%s/oauth/authorize?grant_type=%s&username=%s&password=%s&client_id=%s&response_type=%s&redirect_uri=%s";
    private static final String OAUTH_TOKEN_URL = "%s/oauth/token";
    private static final String CREATE_USER = "%s/api/dex/user";

    @Bean("httpClientRestTemplate")
    public RestTemplate initRestTemplate(){
        return restTemplateBuilder.basicAuthentication("123456","123456").build();
    }

    public Object getHttpForCode(String rootUrl, Map<String,Object> params,Class<?> classType){
        return restTemplate.getForObject(rootUrl,classType,params);
    }

    public Object getHttpTokenByCode(String rootUrl,Map<String,Object> params,Class<?> classType){
        return restTemplate.postForObject(rootUrl,params,classType);
    }

    public Object refreshTokenByRefreshToken(String rootUrl,Map<String,Object> params,Class<?> classType){
        return restTemplate.postForObject(rootUrl,params,classType);
    }

    public boolean putForObject(String rootUrl,Map<String,Object> params,Class<?> classType){
        restTemplate.put(rootUrl,null,params);
        return true;
    }

    public Object getHttpTokenByClient(String rootUrl,Map<String,Object> params,Class<?> classType){
        // 客户端授权模式需要使用表单形式调用，否则或报错
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        params.entrySet().stream().forEach(x->{
            map.add(x.getKey(),x.getValue().toString());
        });
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
                map, headers);
        return restTemplate.postForObject(rootUrl,request,classType);
    }
    /**
     * 初始化授权码模式的请求url
     * */
    public String initAccessTokenUrl(String baseUrl,String clientId,String grantType,String redirectUrl,String clientSecret,String code){
        return String.format(ACCESS_TOKEN_URL,baseUrl,clientId,grantType,redirectUrl,clientSecret,code);
    }

    /**
     * 初始化获取授权码code的url
     * */
    public String initOauthAuthorizeUrl(String baseUrl,String grantType,String userName,String passWord,String clientId,String responseType,String redirectUrl){
        return String.format(OAUTH_AUTHORIZE_URL,baseUrl,grantType,userName,passWord,clientId,responseType,redirectUrl);
    }

    /**
     * 初始化刷新token的url
     * */
    public String initRefreshTokenUrl(String baseUrl,String grantType,String refreshToken,String clientId,String clientSecret){
        return String.format(REFRESH_TOKEN_URL,baseUrl,grantType,refreshToken,clientId,clientSecret);
    }

    /**
     * 初始化token链接
     * */
    public String initOauthTokenUrl(String baseUrl){
        return String.format(OAUTH_TOKEN_URL,baseUrl);
    }
}
