package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    //查询用户名是否存在
    int checkUsername(String username);

    int checkEmail(String email);
    //用户登录
    User selectLogin(@Param("username")String username,@Param("password") String password);
}