package com.example.collectiontableadministration.controller;

import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杨子涵
 * 用户登录控制层接口
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 用户登录接口
     * @param user 用户实体类
     * @return token令牌
     */
    @PostMapping("/login")
    private String login(@RequestBody User user) throws Exception {
        return loginService.login(user);
    }

}
