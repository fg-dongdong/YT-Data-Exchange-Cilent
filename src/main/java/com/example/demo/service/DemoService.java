package com.example.demo.service;

import com.example.demo.oauth2service.ClientCredentialsService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;
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

    // dex 服务地址
    @Value("${dex.rootUrl}")
    private String rootUrl;

    // 获取指标及属性信息调用地址
    private static String infoUrl = "%s/api/dex/resources/info";
    // 获取指标及属性信息调用地址
    private static String infoChangeUrl = "%s/api/dex/resources/infoChange?version=%s";
    // 获取客户组织结构
    private static String pullDexOrgTree = "%s/api/dex/resources/orgTree";
    // 获取客户组织结构变更
    private static String pullDexOrgChange = "%s/api/dex/resources/orgChange?version=%s";
    // 获取客户组织结构
    private static String pullAnalysisInfo = "%s/api/dex/resources/analysis/info?orgIds=%s&dataPeriods=%s&attribute=%s";
    // 获取分析结果
    private static String pullAnalysis = "%s/api/dex/resources/analysis/batch?orgIds=%s&dataPeriods=%s&pageNo=%s&pageSize=%s&attribute=%s";
    // 获取原始数据
    private static String pullData = "%s/api/dex/resources/data?date=%s";
    // 获取客户NameList
    private static String fetchDeveloperNameListUrl = "%s/api/dex/resources/name_list?date=%s";
    // 获取VVOOK系统内NameList
    private static String pullDexResearchNameListUrl = "%s/api/dex/resources/namelistresearch?date=%s";

    /**
     * 查询指标和属性
     */
    public JSONObject pullInfo() {
        // 初始化调用地址
        String url = String.format(infoUrl, rootUrl);
        log.info("请求地址：{}", url);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果: {}", response.getBody());

        return new JSONObject(response.getBody());
    }

    /**
     * 查询指标属性变更
     */
    public JSONObject pullInfoChange(Long version) {
        // 初始化调用地址
        String url = String.format(infoChangeUrl, rootUrl, version);
        log.info("请求地址：{}", url);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果: {}", response.getBody());

        return new JSONObject(response.getBody());
    }

    /**
     * 查询组织结构
     */
    public JSONObject pullDexOrgTree() {
        // 初始化调用地址
        String url = String.format(pullDexOrgTree, rootUrl);
        log.info("请求地址：{}", url);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果: {}", response.getBody());
        return new JSONObject(response.getBody());
    }

    /**
     * 查询组织结构变更
     */
    public JSONObject pullDexOrgChange(Long version) {
        // 初始化调用地址
        String url = String.format(pullDexOrgChange, rootUrl, version);
        log.info("请求地址：{}", url);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果: {}", response.getBody());
        return new JSONObject(response.getBody());
    }

    /**
     * 查询分析结果统计信息
     */
    public JSONObject pullAnalysisInfo(String orgIds, String dataPeriods) {
        // 初始化调用地址
        String url = String.format(pullAnalysisInfo, rootUrl, orgIds, dataPeriods, "");
        log.info("请求地址：{}", url);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果: {}", response.getBody());
        return new JSONObject(response.getBody());
    }

    /**
     * 查询分析结果
     */
    public JSONObject pullAnalysis(String orgIds, String dataPeriods, Integer pageNo, Integer pageSize) {
        // 初始化调用地址
        String url = String.format(pullAnalysis, rootUrl, orgIds, dataPeriods, pageNo, pageSize, "");
        log.info("请求地址：{}", url);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果: {}", response.getBody());
        return new JSONObject(response.getBody());
    }

    /**
     * 查询原始数据
     */
    public JSONObject pullData(String date) {
        // 初始化调用地址
        String url = String.format(pullData, rootUrl, date);
        log.info("请求地址：{}", url);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果: {}", response.getBody());
        return new JSONObject(response.getBody());
    }

    public JSONObject pullDeveloperPurifiedNameList(String date) {
        // 初始化调用地址
        String url = String.format(fetchDeveloperNameListUrl, rootUrl, date);
        log.info("请求地址：{}", url);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果: {}", response.getBody());
        if (response.getStatusCodeValue() == 200)
            return new JSONObject();
        return new JSONObject();
    }

    public JSONObject pullDexResearchNameList(String date) {
        // 初始化调用地址
        String url = String.format(pullDexResearchNameListUrl, rootUrl, date);
        log.info("请求地址：{}", url);
        ResponseEntity<Map> response = getMapResponseEntity(url);
        log.info("请求结果: {}", response.getBody());
        return new JSONObject(response.getBody());
    }

    /**
     * 推送名单
     */
    public JSONObject pushNameList(List<Map> nameList) {
        return null;
    }

    private ResponseEntity<Map> getMapResponseEntity(String url) {
        // 判断是否配置了客户端模式的基础信息
        if (clientMsgIsNull()) {
            throw new NullPointerException("客户端信息缺失");
        }
        // 1、目前YtDataExchange只支持客户端进行Oauth2中客户端授权模式的访问，因此在访问之前需要先获得客户端授权模式下的access_token
        String accessToken = clientCredentialsService.getTokenByClientCredentials(clientId, clientSecret);

        // 2、 使用请求headers中添加参数
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        // 可以自行设置返回的对象class，可以自动映射返回json格式的数据到对象
        RestTemplate restTemplate = restTemplateBuilder.build();//初始化调用模板
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
    }

    private boolean clientMsgIsNull() {
        return StringUtils.isEmpty(this.clientId) || StringUtils.isEmpty(this.clientSecret);
    }

}
