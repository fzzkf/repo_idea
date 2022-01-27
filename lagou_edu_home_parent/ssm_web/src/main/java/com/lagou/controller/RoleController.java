package com.lagou.controller;

import com.lagou.domain.*;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role){

        List<Role> roles = roleService.findAllRole(role);
        return new ResponseResult(true,200,"响应成功",roles);

    }

    @Autowired
    private MenuService menuService;

    /*
        查询所有父子菜单信息
     */
    @RequestMapping("findAllMenu")
    public ResponseResult findAllMenu(){
        //-1 表示查询所有父子级菜单
        List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);

        //响应数据
        HashMap<Object, Object> map = new HashMap<>();
        map.put("parentMenuList",subMenuListByPid);
        return new ResponseResult(true,200,"响应成功",map);
    }

    /*
        根据角色id查询该角色关联的菜单信息id【1,2,3,4,5】
     */
    @RequestMapping("findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId){
        List<Integer> list = roleService.findMenuByRoleId(roleId);
        return new ResponseResult(true,200,"查询成功",list);
    }

    /*
        为角色分配菜单
     */
    @RequestMapping("RoleContextMenu")
    public ResponseResult RoleContextMenu(@RequestBody RoleMenuVO roleMenuVO){
        roleService.RoleContextMenu(roleMenuVO);
        return new ResponseResult(true,200,"响应成功",null);
    }

    /*
        删除角色
     */
    @RequestMapping("deleteRole")
    public ResponseResult deleteRole(Integer id){
        roleService.deleteRole(id);
        return new ResponseResult(true,200,"响应成功",null);
    }
}
