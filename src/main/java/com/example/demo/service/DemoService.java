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
     * 使用url传参形式访问资源
     * */
    private String accessTokenParam = "?access_token=%s";

    /**
     * dex-changge 服务地址
     * */
    @Value("${dex.rootUrl}")
    private String rootUrl;

    /**
     * 获取分析信息调用地址
     */
    private String realEstateNetNeuronUrl="%s/api/dex/resources/index";

    /**
     * 获取业主类型调用地址
     * */
    private String pullAttributesUrl = "%s/api/dex/resources/attributes";

    /**
     * 获取客户组织结构
     * */
    private String pullDexOrgTree = "%s/api/dex/resources/orgTree";

    /**
     * 获取分析结果
     * */
    private String pullAnalysis = "%s/api/dex/resources/analysis?index=%s&dataPeriod=%s";

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

    /**
     * 查询分析结果信息
     * */
    public Map<String,Object> pullAnalysis(String index,String dataPeriod,String attributeType,String attributeValue){
        // 初始化调用地址
        StringBuilder url = new StringBuilder(String.format(pullAnalysis,rootUrl,index,dataPeriod));
        if(!StringUtils.isEmpty(attributeType)){
            url.append("&attributeType=");
            url.append(attributeType);
        }
        if(!StringUtils.isEmpty(attributeValue)){
            url.append("&attributeValue=");
            url.append(attributeValue);
        }
        ResponseEntity<Map> response = getMapResponseEntity(url.toString());
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
