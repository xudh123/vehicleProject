package com.example.admin.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisService{

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 使用阿里巴巴的fastjson进行数据类型转换
     * 使用stringRedisTemplate操作redis
     * */

    /**
     * @param key
     * @return boolean
     * 删除redis中的键值
     * */
    public <T> boolean delete(String key){
        try{
            stringRedisTemplate.delete(key);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public <T> boolean set(String key ,T value){
        try {
            //任意类型转换成String
            String val = beanToString(value);
            if(val==null||val.length()<=0){
                return false;
            }
            stringRedisTemplate.opsForValue().set(key,val);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 添加List对象到redis
     * @param key
     * @return
     * */
    public <T> boolean addList(String key, List<T> tList){
        try {
            //任意类型转换成String
            //String val = beanToString(value);
            /*if(val==null||val.length()<=0){
                return false;
            }*/
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(tList));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 获取redis对象
     * @param key
     * @param classz
     * @return
     * */
    public <T> List<T> getList(String key, Class<T> classz){
        try {
            String tList = stringRedisTemplate.opsForValue().get(key);

            return JSON.parseArray(tList, classz);
        }catch (Exception e){
            return null ;
        }
    }

    public <T> T get(String key,Class<T> clazz){
        try {
            String value = stringRedisTemplate.opsForValue().get(key);

            return stringToBean(value,clazz);
        }catch (Exception e){
            return null ;
        }
    }

    /**
     * 字符串对象转换成object对象
     */
    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String value, Class<T> clazz) {
        if(value==null||value.length()<=0||clazz==null){
            return null;
        }

        if(clazz ==int.class ||clazz==Integer.class){
            return (T)Integer.valueOf(value);
        }
        else if(clazz==long.class||clazz==Long.class){
            return (T)Long.valueOf(value);
        }
        else if(clazz==String.class){
            return (T)value;
        }else {
            return JSON.toJavaObject(JSON.parseObject(value),clazz);
        }
    }

    /**
     * @return String
     * object对象转化成字符串
     */
    private <T> String beanToString(T value) {

        if(value==null){
            return null;
        }
        Class <?> clazz = value.getClass();
        if(clazz==int.class||clazz==Integer.class){
            return ""+value;
        }
        else if(clazz==long.class||clazz==Long.class){
            return ""+value;
        }
        else if(clazz==String.class){
            return (String)value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    /**
    * 获取 key 对应的字符串
    * @param key
    * @return
    */

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }



}
