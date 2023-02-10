package com.example.collectiontableadministration.interceptor;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 杨子涵
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ----------- 加上这个就好了 -----------
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)){
         //   sendResponse(response,"请勿非法请求");
            throw new RuntimeException("请勿非法请求");
        }
        if(!ObjectUtil.isNotEmpty(JwtUtils.checkJwt(token))){
            throw new RuntimeException("登录过期,请重新登录");
        }
        Claims claims = JwtUtils.checkJwt(token);
        User userJson = JSON.parseObject((String) claims.get("userJson"), User.class);
        request.setAttribute("userId",userJson.getId());
        return true;
    }


    public void  sendResponse(HttpServletResponse response,String msg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        JSONObject res = new JSONObject();
        res.put("code",400);
        res.put("message",msg);
        out = response.getWriter();
        out.append(res.toString());
    }

}
