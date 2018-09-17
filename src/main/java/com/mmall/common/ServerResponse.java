package com.mmall.common;
/*
服务器响应
 */
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import java.io.Serializable;
//在类上注入此标志时，返回此类时，会返回set属性里的值
//保证序列化json的时候，如果是null的对象，key也会消失；
//举例说明：JSON原来经过JACKSON转换以后为{"name"："name","sex":null}
//加入注解后，结果为{"name"："name"}
//sex节点被去掉了
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private int status;//登录状态
    private String msg;//返回信息
    private T data;
    private ServerResponse(int status){
        this.status = status;
    }
    private ServerResponse(int status,T data){
        this.status = status;
        this.data = data;
    }
    private ServerResponse(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore //使之不在json序列化结果当中
    public boolean isSuccess(){
        //ResponseCode.SUCCESS.getCode()获取的是枚举类的0，就返回true
        return this.status == ResponseCode.SUCCESS.getCode();
    }
    public int getStatus(){
        return status;
    }
    public String getMsg(){
        return msg;
    }
    public T getData(){
        return data;
    }

    //通过成果的标记创建对象
    public static <T> ServerResponse<T> createBySeccuss(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    //成功后返回一个文本，供前端提示使用
    public static <T> ServerResponse<T> createBySeccussMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
}
    //当服务器创建成功，将data放进去
    public static <T> ServerResponse<T> createBySeccuss(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }
    //消息和数据
    public static <T> ServerResponse<T> createBySeccuss(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    //错误消息函数
    public static <T> ServerResponse<T> createByError(){
        return  new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponse<T> createByErrorMessage(String errorMassage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMassage);
    }
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMassage){
        return  new ServerResponse<T>(errorCode,errorMassage);
    }
}
