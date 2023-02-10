package com.example.collectiontableadministration.domain.request;

/**
 * @author lhy 微服务同意返回值
 */
public enum RequestMsgEnum {
     //操作成功
     SUCCESS(200,"操作成功")

     ;

      final int code;

      final String msg;

     RequestMsgEnum(int code, String msg) {
          this.code = code;
          this.msg = msg;
     }

     public int getCode() {
          return code;
     }

     public String getMsg() {
          return msg;
     }
}
