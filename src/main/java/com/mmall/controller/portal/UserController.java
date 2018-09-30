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

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.util.UUID;

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
       /*
       登录功能
        */
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
        //value：   指定请求的实际地址，指定的地址可以是URI Template 模式（后面将会说明）；
        //method：  指定请求的method类型， GET、POST、PUT、DELETE等；
        @RequestMapping(value = "logout.do",method =RequestMethod.POST)
        @ResponseBody
        public ServerResponse < String > logout(HttpSession session) {
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
        /*
        获取用户信息
         */
        @RequestMapping(value = "get_user_info.do",method =RequestMethod.POST)
        @ResponseBody
        public ServerResponse<User> getUserInfo(HttpSession session){
                User user = (User) session.getAttribute(Const.CURRENT_USER);
                if(user != null){
                    return ServerResponse.createBySeccuss(user);
                }
                return  ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        /*
        获取密码提示问题
         */
        @RequestMapping(value = "forget_get_question.do",method =RequestMethod.POST)
        @ResponseBody
        public ServerResponse<String> forgetGerQuestion(String username){
                return iUserService.selectQuestion(username);
        }
        //使用本地缓存检查问题答案的接口
        @RequestMapping(value = "forget_check_answer.do",method =RequestMethod.GET)
        @ResponseBody
        public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
                return iUserService.checkAnswer(username,question,answer);
        }
        //忘记密码后的重置密码
        @RequestMapping(value = "forget_reset_password.do",method =RequestMethod.GET)
        @ResponseBody
        public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
                return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
        }
        @RequestMapping(value = "reset_password.do",method =RequestMethod.GET)
        @ResponseBody
        //登录状态的重置密码
        public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
            User user = (User)session.getAttribute(Const.CURRENT_USER);
            if(user == null){
                return ServerResponse.createByErrorMessage("用户未登录");
            }
            return iUserService.resetPassword(passwordOld,passwordNew,user);
        }
        @RequestMapping(value = "update_information.do",method =RequestMethod.GET)
        @ResponseBody
        //更新个人信息
        public ServerResponse<User> update_Information(HttpSession session,User user){
                //获取当前用户的id，防止id被变化，解决越权问题
                User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
                if(currentUser == null){
                        return ServerResponse.createByErrorMessage("用户未登录");
                }
                user.setId(currentUser.getId());
                //username也不能被更新
                user.setUsername(currentUser.getUsername());
                ServerResponse<User> response = iUserService.updateInformation(user);
                if(response.isSuccess()){
                        session.setAttribute(Const.CURRENT_USER,response.getData());
                }
                return response;
        }
}
