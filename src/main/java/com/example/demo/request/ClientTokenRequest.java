package com.example.demo.request;

import com.example.demo.response.AccessTokenReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ClientTokenRequest
 * @Description
 * @Date 2019/5/9 10:44
 * @Created by ZLW
 */
@Data
@ToString
public class ClientTokenRequest {

    /**
     * 授权类型
     * */
    @JsonProperty("grant_type")
    private String grantType;

    /**
     * 权限类型
     * */
    @JsonProperty("scope")
    private String scope;

    /**
     * 客户端id
     * */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 客户端密码
     * */
    @JsonProperty("client_secret")
    private String clientSecret;
}
