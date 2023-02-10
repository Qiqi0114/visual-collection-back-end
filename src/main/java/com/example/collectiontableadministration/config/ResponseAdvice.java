package com.example.collectiontableadministration.config;


import com.example.collectiontableadministration.annotation.PreventResultAnnotation;
import com.example.collectiontableadministration.domain.request.RequestMsgEnum;
import com.example.collectiontableadministration.domain.response.ReturnValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author 全局统一返回
 */
@RestControllerAdvice
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    /**
     * @param returnType 参1
     * @param converterType  参2
     * @return 是否统一返回
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(PreventResultAnnotation.class)||!returnType.getParameterType().isAssignableFrom(ReturnValue.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 否则直接包装成ResultVo返回
        return  ReturnValue.success(RequestMsgEnum.SUCCESS.getCode(),RequestMsgEnum.SUCCESS.getMsg(),body);
    }


}