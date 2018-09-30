package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
//标记登录状态缓存
public class TokenCache {
    private  static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX = "token_";
    //设置缓存的大小、有效期
    //LRU算法
    private static LoadingCache<String,String> localCache =
            ////CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
            CacheBuilder.newBuilder()
            //设置cache的初始大小为1000，要合理设置该值
            .initialCapacity(1000)
            //设置缓存最大容量为10000，超过10000之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(10000)
            //设置该缓存的有效期
            .expireAfterAccess(12,TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法进行加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });
    public static  void setKey(String key,String value){
        //put()方法：可以直线显示地向缓存中插入值，这会直接覆盖掉已有键之前映射的值。
        localCache.put(key,value);
    }
    public static String getKey(String key){
        String value = null;
        try {
            value = localCache.get(key);
            if("null".equals(value)){
                return null;
            }
            return value;
        }catch (Exception e){
            logger.error("localCache get error",e);
        }
        return null;
    }
}
