package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
    public void pullAnalysisTest(){
        demoService.setClientId(this.clientId);
        demoService.setClientSecret(this.clientSecret);
        log.info("返回结果：{}",demoService.pullAnalysis("1","2018-06","",""));
    }
}
