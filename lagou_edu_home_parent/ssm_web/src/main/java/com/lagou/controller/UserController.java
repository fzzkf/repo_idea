package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVO;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVO userVO){
        PageInfo pageInfo = userService.findAllUserByPage(userVO);
        return new ResponseResult(true,200,"响应成功",pageInfo);
    }

    @RequestMapping("updateUserStatus")
    public ResponseResult updateUserStatus(Integer id,String status){
        userService.updateUserStatus(id,status);
        return new ResponseResult(true,200,"响应成功",status);
    }

    /*
        用户登录
     */
    @RequestMapping("login")
    public ResponseResult login(User user, HttpSession session) throws Exception {
        User user1 = userService.login(user);
        if (user1!=null){
            //登陆成功 保存用户id，以及access_token到session中
            session.setAttribute("user_id",user1.getId());
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token",access_token);

            HashMap<String, Object> map = new HashMap<>();
            map.put("access_token",access_token);
            map.put("user_id",user1.getId());
            //将查询出的user1放到map中返回给前台
            map.put("user",user1);
            return new ResponseResult(true,1,"登陆成功",map);

        }else{
            return new ResponseResult(true,400,"用户名或者密码错误",null);
        }
    }

    /*
        分配角色（回显）
     */
    @RequestMapping("findUserRoleById")
    public ResponseResult findUserRoleById(Integer id){
        List<Role> roles = userService.findUserRelationRoleById(id);
        return new ResponseResult(true,200,"响应成功",roles);
    }

    /*
        分配角色
     */
    @RequestMapping("userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVO userVO){
        userService.userContextRole(userVO);
        return new ResponseResult(true,200,"响应成功",null);
    }

    /*
        获取用户权限，进行菜单动态展示
     */
    @RequestMapping("getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request,HttpSession session){

        //1.获取请求头中的token
        String header_token = request.getHeader("Authorization");
        //2.获取session的token
        String session_token = (String) session.getAttribute("access_token");
        //3.判断token是否一致
        if (header_token.equals(session_token)){
            return userService.getUserPermissions((Integer) session.getAttribute("user_id"));
        }else {
            return new ResponseResult(false,400,"获取失败",null);
        }


    }
}
