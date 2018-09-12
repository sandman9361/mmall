package com.mmall.common;
/*
定义枚举类，响应代码
 */
public enum ResponseCode {
    SUCCESS(0,"SUCCESS"),//成功
    ERROR(1,"ERROR"),//错误
    NEED_LOGIN(10,"NEED_LOGIN"),//需要登录
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");//非法参数

    private final int code;//提示编码（0,1,10,2）
    private final String desc;//提示编码对应的信息

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
