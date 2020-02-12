package com.example.demo.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname InMemoryRefreshTokenServices
 * @Description
 * @Date 2019/5/8 10:25
 * @Created by ZLW
 */
@Service
public class InMemoryRefreshTokenServices implements RefreshTokenService {
    /**
     * 用于存储refresh_code的集合
     */
    private final ConcurrentHashMap<String, String> refreshTokenMap = new ConcurrentHashMap<>();

    @Override
    public boolean addCode(String name, String code) {
        refreshTokenMap.put(name, code);
        return false;
    }

    @Override
    public boolean removeCode(String name, String code) {
        return refreshTokenMap.remove(name, code);
    }

    @Override
    public boolean removeCodeByName(String name) {
        refreshTokenMap.remove(name);
        return true;
    }

    @Override
    public boolean haveCodeByName(String name) {
        String key = refreshTokenMap.get(name);
        return !StringUtils.isEmpty(key);
    }
}
