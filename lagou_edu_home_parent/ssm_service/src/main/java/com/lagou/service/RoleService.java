package com.lagou.service;


import com.lagou.domain.Menu;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVO;

import java.util.List;

public interface RoleService {

    /*
        条件查询所有角色
     */
    public List<Role> findAllRole(Role role);

    /*
        根据角色id查询该角色关联的菜单信息id【1,2,3,4,5】
     */
    public List<Integer> findMenuByRoleId(Integer roleId);

    /*
        为角色分配菜单
     */
    public void RoleContextMenu(RoleMenuVO roleMenuVO);

    /*
        删除角色
     */
    public void deleteRole(Integer roleId);

}
