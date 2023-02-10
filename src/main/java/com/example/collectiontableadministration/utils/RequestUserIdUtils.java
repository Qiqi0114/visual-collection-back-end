package com.example.collectiontableadministration.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨子涵
 * 获取登录的userId
 */
@Component
public class RequestUserIdUtils {
  @Autowired
  private HttpServletRequest request;

  public Long getUserId(){
      return (Long) request.getAttribute("userId");
  }


}
