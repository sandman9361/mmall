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
    //根据用户名找回密码问题
    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username")String username,@Param("question")String question,@Param("answer")String answer);

    int updatePasswordByUsername(@Param("username")String username,@Param("passwordNew")String passwordNew);

    int checkPassword(@Param("password") String password,@Param("userId") Integer userId);

    int checkEmailByUser(@Param("email")String email,@Param("userId")Integer userId);
}