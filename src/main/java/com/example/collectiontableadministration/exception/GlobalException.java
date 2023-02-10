package com.example.collectiontableadministration.exception;



import com.example.collectiontableadministration.annotation.PreventResultAnnotation;
import com.example.collectiontableadministration.domain.response.ReturnValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 杨子涵 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
    @PreventResultAnnotation
    public ReturnValue runTimeException(RuntimeException runtimeException){
        runtimeException.printStackTrace();
        return ReturnValue.error(500,runtimeException.getMessage().toString());
    }
    @ExceptionHandler(Exception.class)
    @PreventResultAnnotation
    public ReturnValue runTimeException(Exception exception){
        exception.printStackTrace();
        return ReturnValue.error(500,exception.getMessage());
    }

    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @PreventResultAnnotation
    public ReturnValue exceptionHandler(MissingServletRequestParameterException ex){
        log.error(ex.getMessage());

        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return ReturnValue.error(500,msg);
        }

        return  ReturnValue.error(500,"该异常未定义 联系后端吧 笔芯 有bug很正常");
    }
}
