package com.example.demo.oauth2service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname ClientCredentialsToken
 * @Description
 * @Date 2019/5/10 17:53
 * @Created by ZLW
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCredentialsToken{
    /**
     * access_token
     * */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * token_type
     * */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * expires_in
     * */
    @JsonProperty("expires_in")
    private String expiresIn;

    /**
     * scope
     * */
    @JsonProperty("scope")
    private String scope;
}
