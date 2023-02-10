package com.example.collectiontableadministration.service;

import com.example.collectiontableadministration.domain.pojo.User;

/**
 * @author 杨子涵
 * 登录service
 */
public interface LoginService {
    /**
     * 杨子涵
     * @param user 用户实体类
     * @return token
     * @throws Exception 异常Aes
     */
    String login(User user) throws Exception;
}
