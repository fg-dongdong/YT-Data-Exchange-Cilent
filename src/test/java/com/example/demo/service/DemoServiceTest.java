package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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

    @Before
    public void setClient() {
        demoService.setClientId(this.clientId);
        demoService.setClientSecret(this.clientSecret);
    }

    @Test
    public void pullInfo() {
        log.info("返回结果[JSON]：{}", demoService.pullInfo());
    }

    @Test
    public void pullInfoChange() {
        log.info("返回结果[JSON]：{}", demoService.pullInfoChange(1546272000000L));
    }

    @Test
    public void pullDexOrgTreeTest() {
        log.info("返回结果[JSON]：{}", demoService.pullDexOrgTree());
    }

    @Test
    public void pullDexOrgChangeTest() {
        log.info("返回结果[JSON]：{}", demoService.pullDexOrgChange(1546272000000L));
    }

    @Test
    public void pullAnalysisInfoTest() {
        log.info("返回结果[JSON]：{}", demoService.pullAnalysisInfo("436562,436563,436564"
                , "2019-01,2019-02,2019-03,2019-04,2019-05,2019-06,2019-07,2019-08,2019-09"));
    }

    @Test
    public void pullAnalysisTest() {
        log.info("返回结果[JSON]：{}", demoService.pullAnalysis("436562,436563,436564"
                , "2019-01,2019-02,2019-03,2019-04,2019-05,2019-06,2019-07,2019-08,2019-09", 1, 100));
    }

    @Test
    public void pullDataTest() {
        log.info("返回结果[JSON]：{}", demoService.pullData("2019-12-16"));
    }

    @Test
    public void pullDeveloperPurifiedNameList() { // from developer
        log.info("返回结果[JSON]：{}", demoService.pullDeveloperPurifiedNameList("2019-12-16"));
    }

    @Test
    public void pullDexResearchNameList() { // from dex
        log.info("返回结果[JSON]：{}", demoService.pullDexResearchNameList("2019-12-16"));
    }
}
