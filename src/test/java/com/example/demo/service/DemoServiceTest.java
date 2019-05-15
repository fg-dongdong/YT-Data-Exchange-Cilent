package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    private String clientId = "dex_jingrui";
    private String clientSecret = "ZCEMSLQVBDKPPOYWZUAGUT";

    @Test
    public void testGreenTest(){
        demoService.setClientId(this.clientId);
        demoService.setClientSecret(this.clientSecret);
        if(demoService.serverIsOk()){
            log.info("服务可用");
        }else{
            log.info("服务不可用");
        }
    }

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
//        demoService.setClientId(this.clientId);
//        demoService.setClientSecret(this.clientSecret);
        log.info("返回结果：{}",demoService.pullDexOrgTree());
    }
}
