package com.example.collectiontableadministration.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import com.example.collectiontableadministration.utils.RequestUserIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * @author mybattis 自动填充
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    private RequestUserIdUtils requestUserIdUtils;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",requestUserIdUtils.getUserId());
        metaObject.setValue("createTime",LocalDateTime.now());
        metaObject.setValue("createUser",requestUserIdUtils.getUserId());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",requestUserIdUtils.getUserId());
    }
   private boolean getObj(Object o) {
       Class<?> aClass = o.getClass();
       try {
           Field updateTime = aClass.getDeclaredField("updateTime");
           return true;
       } catch (NoSuchFieldException e) {
           return false;
       }
   }

}