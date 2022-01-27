package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVO;

import java.util.List;

public interface UserService {

    /*
        用户分页以及多条件查询
     */
    public PageInfo findAllUserByPage(UserVO userVO);

    /*
        改变用户状态
     */
    public void updateUserStatus(Integer id,String status);

    /*
        用户登录：根据用户名查询具体用户信息
     */
    public User login(User user) throws Exception;

    /*
        分配角色（回显）
     */
    public List<Role> findUserRelationRoleById(Integer id);

    /*
        用户关联角色
     */
    public void userContextRole(UserVO userVO);

    /*
        获取用户权限，进行菜单动态展示
     */
    public ResponseResult getUserPermissions(Integer userId);
}
