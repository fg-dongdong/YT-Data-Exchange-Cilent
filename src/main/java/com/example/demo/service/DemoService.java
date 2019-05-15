package com.example.demo.service;

import com.example.demo.oauth2service.ClientCredentialsService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.util.Map;

/**
 * @Classname DemoService
 * @Description 用于演示如何获取YtDataExchange服务
 * @Date 2019/5/10 18:00
 * @Created by ZLW
 */
@Service
@Slf4j
public class DemoService {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;//访问外部Rest服务的初始化类
    @Autowired
    ClientCredentialsService clientCredentialsService;
    @Setter
    private String clientId;
    @Setter
    private String clientSecret;

    /**
     * 验证非保护资源是否可以访问
     * */
    private String testNoTokenUrl = "http://localhost:10106/greenTest";
    /**
     * 验证受保护资源是否可访问
     * */
    private String testTokenUrl = "http://localhost:10106/api/hello";
    /**
     * 使用url传参形式访问资源
     * */
    private String accessTokenParam = "?access_token=%s";

    /**
     * dex-changge 服务地址
     * */
    private String rootUrl = "http://localhost:10106";

    /**
     * 获取分析信息调用地址
     */
    private String realEstateNetNeuronUrl="%s/api/dex/resources/realEstateNetNeuron";

    /**
     * 获取业主类型调用地址
     * */
    private String pullAttributesUrl = "%s/api/dex/resources/attributes";

    /**
     * 获取客户组织结构
     * */
    private String pullDexOrgTree = "%s/api/dex/resources/dexOrgTree";

    /**
     * 用于测试Oauth2服务的资源是否正常
     * */
    public boolean serverIsOk(){
        // 1、目前YtDataExchange只支持客户端进行Oauth2中客户端授权模式的访问，因此在访问之前需要先获得客户端授权模式下的access_token
        String accessToken = clientCredentialsService.getTokenByClientCredentials(clientId,clientSecret);
        // 2、获取access_token以后，有两种方式进行资源访问
        RestTemplate restTemplate = restTemplateBuilder.build();//初始化调用模板
        // 2.0 非限制资源访问，测试服务器是否正常
        String url = testNoTokenUrl;
        String returnMsg = restTemplate.getForObject(url,String.class);
        log.info("访问非资源结果:{}",returnMsg);

        // 2.1 使用{请求连接+?access_token={access_token}}方式
        url = testTokenUrl + String.format(accessTokenParam,accessToken);
        returnMsg = restTemplate.getForObject(url,String.class);
        log.info("使用url传入token调用保护资源结果:{}",returnMsg);
        // 2.2 使用请求headers中添加参数
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization","Bearer "+accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,requestEntity,String.class);
        log.info("使用请求头传入token调用保护资源结果:{}",response.getBody());
        return true;
    }

    /**
     * 获取分析结果
     * */
    public Map<String,Object> realEstateNetNeuron(){
        // 初始化调用地址
        String url = String.format(realEstateNetNeuronUrl,rootUrl);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果:{}",response.getBody());
        return response.getBody();
    }

    /**
     * 查询业主类型信息
     * */
    public Map<String,Object> pullAttributes(){
        // 初始化调用地址
        String url = String.format(pullAttributesUrl,rootUrl);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果:{}",response.getBody());
        return response.getBody();
    }

    /**
     * 查询组织结构
     * */
    public Map<String,Object> pullDexOrgTree(){
        // 初始化调用地址
        String url = String.format(pullDexOrgTree,rootUrl);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果:{}",response.getBody());
        return response.getBody();
    }

    private ResponseEntity<Map> getMapResponseEntity(String url) {
        // 判断是否配置了客户端模式的基础信息
        if(clientMsgIsNull()){
            throw new NullPointerException("客户端信息缺失");
        }
        // 1、目前YtDataExchange只支持客户端进行Oauth2中客户端授权模式的访问，因此在访问之前需要先获得客户端授权模式下的access_token
        String accessToken = clientCredentialsService.getTokenByClientCredentials(clientId, clientSecret);

        RestTemplate restTemplate = restTemplateBuilder.build();//初始化调用模板
        // 2、 使用请求headers中添加参数
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        // 可以自行设置返回的对象class，可以自动映射返回json格式的数据到对象
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
    }

    private boolean clientMsgIsNull(){
        return StringUtils.isEmpty(this.clientId) || StringUtils.isEmpty(this.clientSecret);
    }

}
