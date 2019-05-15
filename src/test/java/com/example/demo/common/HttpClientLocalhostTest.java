package com.example.demo.common;

import com.example.demo.DemoApplicationTests;
import com.example.demo.client.HttpClientLocalhost;
import com.example.demo.oauth2service.ClientCredentialsToken;
import com.example.demo.response.AccessTokenReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname HttpClientLocalhostTest
 * @Description
 * @Date 2019/5/8 14:02
 * @Created by ZLW
 */
@Slf4j
public class HttpClientLocalhostTest extends DemoApplicationTests {

    @Autowired
    HttpClientLocalhost httpClientLocalhost;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Test
    public void getHttpTest(){
        String url = httpClientLocalhost.initOauthAuthorizeUrl("http://localhost:10091",
                "authorization_code",
                "123456",
                "123456",
                "123456",
                "code",
                "http://localhost:8080/callback");
        log.info("请求地址:{}",url);
        Map<String,Object> map = new HashMap<>();
        log.info((String) httpClientLocalhost.getHttpForCode(url,map,String.class));
    }

    @Test
    public void getHttpTokenByCodeTest(){
        String url = httpClientLocalhost.initAccessTokenUrl("http://localhost:10091",
                "123456",
                "authorization_code",
                "http://localhost:8080/callback",
                "123456",
                "w54ny7");
        log.info("请求地址:{}",url);
        Map<String,Object> map = new HashMap<>();
        AccessTokenReq accessTokenReq = (AccessTokenReq) httpClientLocalhost.getHttpTokenByCode(url,map, AccessTokenReq.class);
        log.info(accessTokenReq.toString());
    }

    @Test
    public void refreshTokenByRefreshTokenTest(){
        String url = httpClientLocalhost.initRefreshTokenUrl("http://localhost:10091",
                "refresh_token",
                "694b044a-4192-4fea-a796-f0926aad6125",
                "123456",
                "123456");
        log.info("请求地址:{}",url);
        Map<String,Object> map = new HashMap<>();
//        map.put("gran_type","refresh_token");
//        map.put("refresh_token","694b044a-4192-4fea-a796-f0926aad6125");
//        map.put("client_id","123456");
//        map.put("client_secret","123456");
        AccessTokenReq accessTokenReq = (AccessTokenReq) httpClientLocalhost.refreshTokenByRefreshToken(url,map, AccessTokenReq.class);
        log.info(accessTokenReq.toString());
    }

    @Test
    public void getHttpTokenByClientTest() throws JSONException {
        String url = httpClientLocalhost.initOauthTokenUrl("http://localhost:10091");
        log.info("请求地址:{}",url);
        Map<String,Object> map = new HashMap<>();
        map.put("grant_type","client_credentials");
        map.put("scope","all");
        map.put("client_id","client6");
        map.put("client_secret","DAFCAQTMZWQSVECWUQDYZH");
        httpClientLocalhost.setRestTemplate(restTemplateBuilder.build());
//        AccessTokenReq accessTokenReq = (AccessTokenReq) httpClientLocalhost.getHttpTokenByClient(url,map, AccessTokenReq.class);
//        log.info(accessTokenReq.toString());
        log.info(httpClientLocalhost.getHttpTokenByClient(url,map, ClientCredentialsToken.class).toString());
    }

    @Test
    public void getResourcesTest(){
        String url = "http://localhost:10091/api/dex/resources/realEstateNetNeuron?access_token=11318013-fdb7-4d4b-a140-e619d6630982";
        HashMap<String,String> map = (HashMap<String, String>) httpClientLocalhost.getHttpForCode(url, Collections.emptyMap(),HashMap.class);
        log.info("返回值：{}",map);
    }

}
