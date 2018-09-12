package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
//Spring的注解，使UserController起到Controller作用
@Controller
//把请求地址全部设置到/User下,如果写在方法上面，那么下面类中的每个方法都要写这个注解
@RequestMapping("/User/")
public class UserController {
        /**
         * 用户登录
         * @param username
         * @param password
         * @param session
         * @return
         */
        @Autowired
        private  IUserService iUserService;
        //与接口名称保持一致，指定请求只能post方式
        @RequestMapping(value = "login.do",method =RequestMethod.POST)
        //使返回时候，自动通过Spring MVC的jackson插件将我们的返回值序列化成json格式
        @ResponseBody
        public ServerResponse<User> login(String username, String password, HttpSession session){
            //Service-->mybatis->dao
                ServerResponse<User> response = iUserService.login(username,password);
                if(response.isSuccess()){
                        session.setAttribute(Const.CURRENT_USER,response.getData());
                }
            return response;
        }
        /*
        用户登出功能
         */
        @RequestMapping(value = "logout.do",method =RequestMethod.POST)
        @ResponseBody
        public ServerResponse<String> logout(HttpSession session){
                session.removeAttribute(Const.CURRENT_USER);
                return ServerResponse.createBySeccuss();
        }
        /*
        用户注册功能
         */
        @RequestMapping(value = "register.do",method =RequestMethod.POST)
        @ResponseBody
        public ServerResponse<String> register(User user){
                return iUserService.register(user);
        }
        /*
        验证用户信息
         */
        @RequestMapping(value = "check_valid.do",method =RequestMethod.POST)
        @ResponseBody
        public ServerResponse<String> checkValid(String str,String type){
                return iUserService.checkValid(str,type);
        }
}
