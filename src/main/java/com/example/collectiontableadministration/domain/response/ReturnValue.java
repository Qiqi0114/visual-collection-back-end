package com.example.collectiontableadministration.domain.response;

import lombok.Data;

import java.io.Serializable;


/**
 * @author lhy 知识图谱微服务同意返回值
 */
@Data
public class ReturnValue implements Serializable {
    /**
     * 状态码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;

    public ReturnValue(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ReturnValue(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static  ReturnValue success(int code,String msg,Object data){
        return new ReturnValue(code,msg,data);
    }
    public static  ReturnValue success(int code,String msg){
        return new ReturnValue(code,msg);
    }
    public static  ReturnValue error(int code,String msg){
        return new ReturnValue(code,msg);
    }
}
