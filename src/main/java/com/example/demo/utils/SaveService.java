package com.example.demo.utils;

/**
 * @Classname CodeService
 * @Description 用于Oauth相关信息的的存储与获取
 * @Date 2019/5/8 10:03
 * @Created by ZLW
 */
public interface SaveService {

    /**
     * 保存数据
     */
    boolean addCode(String name, String code);

    /**
     * 通过名字移除数据
     */
    boolean removeCode(String name, String code);

    /**
     * 通过名字移除数据
     */
    boolean removeCodeByName(String name);

    /**
     * 是否有可用数据
     */
    boolean haveCodeByName(String name);
}
