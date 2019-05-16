package com.example.demo.oauth2service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname ClientCredentialsService
 * @Description 用于客户端模式下获取token信息
 * @Date 2019/5/10 17:16
 * @Created by ZLW
 */
@Service
@Slf4j
public class ClientCredentialsService {
    @Autowired
    HttpsClientLocal httpsClientLocal;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    @Autowired
    @Qualifier("clientCredentialsRestTemplate")
    RestTemplate restTemplate;


    @Bean("clientCredentialsRestTemplate")
    public RestTemplate initRestTemplate(){
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpsClientLocal.httpClient());
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
    /**
     * 服务器地址
     * */
    @Setter
    @Value("${dex.rootUrl}")
    private String serverHost;
    /**
     * 访问地址(form-data模式)
     * */
    @Setter
    private String url = "%s/oauth/token";

    /**
     * 访问地址(url传参形式)
     * */
    @Setter
    private String paramUrl = "%s/oauth/token?grant_type=client_credentials&client_id=%s&client_secret=%s&scope=%s";

    /**
     * scope，激活后的用户有all权限
     * */
    @Setter
    @Value("${dex.scope}")
    private String scope = "all";

    @Setter
    private boolean isFormData = true;

    /**
     * @param clientId 客户端id
     * @param clientSecret 客户端识别码
     * @return String 返回token信息
     * */
    public String getTokenByClientCredentials(String clientId,String clientSecret){
        if(isFormData){
            return getTokenByFormData(clientId,clientSecret);
        }else{
            return getTokenByUrl(clientId,clientSecret);
        }
    }

    /**
     * 通过url传参的形式获取access_token
     * */
    private String getTokenByUrl(String clientId,String clientSecret){
        ClientCredentialsToken clientCredentialsToken = restTemplate.postForObject(String.format(paramUrl,serverHost,clientId,clientSecret,this.scope),null,ClientCredentialsToken.class);
        if(null != clientCredentialsToken){
            return clientCredentialsToken.getAccessToken();
        }
        return null;
    }

    /**
     * 通过表单的形式获取access_token
     * */
    private String getTokenByFormData(String clientId,String clientSecret){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type","client_credentials");
        map.add("client_id",clientId);
        map.add("client_secret",clientSecret);
        map.add("scope",this.scope);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String rootUrl = String.format(url,serverHost);
        log.info("请求地址：{}",rootUrl);
        ClientCredentialsToken clientCredentialsToken = restTemplate.postForObject(rootUrl,request,ClientCredentialsToken.class);
        if(null != clientCredentialsToken){
            return clientCredentialsToken.getAccessToken();
        }
        return null;
    }


}
