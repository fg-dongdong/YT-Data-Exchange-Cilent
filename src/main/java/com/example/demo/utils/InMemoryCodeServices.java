package com.example.demo.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname InMemoryServices
 * @Description 存储到内存中的授权码
 * @Date 2019/5/8 10:02
 * @Created by ZLW
 */
@Service
public class InMemoryCodeServices implements CodeService {

    /**
     * 用于存储授权码的集合
     */
    private static final ConcurrentHashMap<String, String> codeMap = new ConcurrentHashMap<>();

    @Override
    public boolean addCode(String name, String code) {
        codeMap.put(name, code);
        return true;
    }

    @Override
    public boolean removeCode(String name, String code) {
        return codeMap.remove(name, code);
    }

    @Override
    public boolean removeCodeByName(String name) {
        codeMap.remove(name);
        return true;
    }

    @Override
    public boolean haveCodeByName(String name) {
        String key = codeMap.get(name);
        return !StringUtils.isEmpty(key);
    }
}
