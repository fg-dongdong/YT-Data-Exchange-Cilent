package com.example.demo.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname InMemoryTokenServices
 * @Description
 * @Date 2019/5/8 10:22
 * @Created by ZLW
 */
@Service
public class InMemoryTokenServices implements TokenService {
    /**
     * 用于存储token的集合
     */
    private static final ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

    @Override
    public boolean addCode(String name, String code) {
        tokenMap.put(name, code);
        return false;
    }

    @Override
    public boolean removeCode(String name, String code) {
        return tokenMap.remove(name, code);
    }

    @Override
    public boolean removeCodeByName(String name) {
        tokenMap.remove(name);
        return true;
    }

    @Override
    public boolean haveCodeByName(String name) {
        String key = tokenMap.get(name);
        return !StringUtils.isEmpty(key);
    }
}
