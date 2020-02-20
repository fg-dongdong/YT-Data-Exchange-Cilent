package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import com.example.demo.utils.HttpErrorUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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

    @Before
    public void setClient() {
        demoService.setClientId(this.clientId);
        demoService.setClientSecret(this.clientSecret);
    }

    @Test
    public void pullInfo() {
        log.info("返回结果：{}", demoService.pullInfo());
    }

    @Test
    public void pullDexOrgTreeTest() {
        log.info("返回结果：{}", demoService.pullDexOrgTree());
    }

    @Test
    public void pullDexOrgChangeTest() {
        log.info("返回结果：{}", demoService.pullDexOrgChange(1546272000000L));
    }

    @Test
    public void pullAnalysisInfoTest() {
        log.info("返回结果：{}", demoService.pullAnalysisInfo("436562,436563,436564"
                , "2019-01,2019-02,2019-03,2019-04,2019-05,2019-06,2019-07,2019-08,2019-09"));
    }

    @Test
    public void pullAnalysisTest() {
        log.info("返回结果：{}", demoService.pullAnalysis("436562,436563,436564"
                , "2019-01,2019-02,2019-03,2019-04,2019-05,2019-06,2019-07,2019-08,2019-09", 1, 100));
    }
/*
    @Test
    public void pullAnalysisTest() throws IOException {
        try {
            log.info("无属性返回结果：{}", demoService.pullAnalysis("083", "4", "2018-06", "", ""));
            log.info("有属性返回结果：{}", demoService.pullAnalysis("011", "4", "2018-06", "d_owner_type_four", "磨合期"));
        } catch (HttpServerErrorException e) {
            log.error("异常返回错误信息：" + HttpErrorUtils.getDefaultHttpErrorObject(e.getResponseBodyAsString()));
        }
    }

    @Test
    public void pullBatchAnalysisTest() throws IOException {
        try {
            log.info("返回结果：{}", demoService.pullBatchAnalysis("083", "2019-06"));
        } catch (HttpServerErrorException e) {
            log.error("异常返回错误信息：" + HttpErrorUtils.getDefaultHttpErrorObject(e.getResponseBodyAsString()));
        }
    }*/
}
