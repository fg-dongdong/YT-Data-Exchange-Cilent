package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import com.example.demo.oauth2service.ClientCredentialsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname ClientCredentialsServiceTest
 * @Description
 * @Date 2019/5/10 17:35
 * @Created by ZLW
 */
@Slf4j
public class ClientCredentialsServiceTest extends DemoApplicationTests {
    @Autowired
    ClientCredentialsService clientCredentialsService;

    @Test
    public void getTokenTest(){
      log.info("获取token：{}",clientCredentialsService.getTokenByClientCredentials("test3","ZWEFWJRFBCRZUXCLKKKUQQ"));
    }
}
