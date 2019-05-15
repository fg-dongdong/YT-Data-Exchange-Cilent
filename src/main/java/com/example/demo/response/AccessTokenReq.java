package com.example.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @Classname AccessTokenReq
 * @Description
 * @Date 2019/5/7 14:22
 * @Created by ZLW
 *
 * {
 *     "access_token": "9287ef7f-84c6-455c-b8a8-1ca43e56b03f",
 *     "token_type": "bearer",
 *     "refresh_token": "857d01f1-75e3-4c16-8e07-4ae9ff46ed8e",
 *     "expires_in": 9999,
 *     "scope": "all"
 * }
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenReq {

    /**
     * 令牌
     * */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 令牌类型
     * */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * 刷新令牌
     * */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 随机数
     * */
    @JsonProperty("expires_in")
    private int expiresIn;

    /**
     * 权限
     * */
    @JsonProperty("scope")
    private String scope;
}
