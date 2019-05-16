package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import com.example.demo.oauth2service.HttpErrorPage;
import com.example.demo.utils.HttpErrorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

/**
 * @Classname DemoServiceTest
 * @Description
 * @Date 2019/5/13 14:22
 * @Created by ZLW
 */

@Slf4j
public class DemoServiceTest extends DemoApplicationTests {
    @Autowired
    DemoService demoService;
    @Value("${dex.clientId}")
    private String clientId;
    @Value("${dex.clientSecret}")
    private String clientSecret;

    @Test
    public void realEstateNetNeuronTest(){
        demoService.setClientId(this.clientId);
        demoService.setClientSecret(this.clientSecret);
        log.info("返回结果：{}",demoService.realEstateNetNeuron());
    }

    @Test
    public void pullAttributesTest(){
        demoService.setClientId(this.clientId);
        demoService.setClientSecret(this.clientSecret);
        log.info("返回结果：{}",demoService.pullAttributes());
    }

    @Test
    public void pullDexOrgTreeTest(){
        demoService.setClientId(this.clientId);
        demoService.setClientSecret(this.clientSecret);
        log.info("返回结果：{}",demoService.pullDexOrgTree());
    }

    @Test
    public void pullAnalysisTest() throws IOException {
        demoService.setClientId(this.clientId);
        demoService.setClientSecret(this.clientSecret);
        try{
            log.info("无属性返回结果：{}",demoService.pullAnalysis("083","4","2018-06","",""));
            log.info("有属性返回结果：{}",demoService.pullAnalysis("011","4","2018-06","d_owner_type_four","磨合期"));
        }catch (HttpServerErrorException e){
            log.error("异常返回错误信息："+ HttpErrorUtils.getDefaultHttpErrorObject(e.getResponseBodyAsString()));
        }

    }
}
