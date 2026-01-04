package com.lvr.ihave.web.controller;

import com.lvr.ihave.annotation.AdminToken;
import com.lvr.ihave.business.service.*;
import com.lvr.ihave.pojo.*;
import com.lvr.ihave.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * 用户列表
     * @param model
     * @return
     */
    @AdminToken
    @GetMapping("/list")
    public String userList(Model model){
        List<SysUser> userList = userService.getUserList();
        //将用户列表添加到模型中
        model.addAttribute("userList",userList);
        //跳转到用户列表页面
        return "user/list";
    }

    /**
     * 进入新增/编辑用户页面
     * @param id 用户ID
     * @param model 模型
     * @return
     */
    @AdminToken
    @GetMapping("/edit")
    public String editUser(@RequestParam(value = "userId", required = false) String userId, Model model){
        if(userId != null && !userId.isEmpty()){
            // 修改操作，查询用户信息
            SysUser user = userService.selectByPrimaryKey(userId);
            model.addAttribute("user", user);
        } else {
            // 新增操作，创建空用户对象
            SysUser user = new SysUser();
            model.addAttribute("user", user);
        }
        
        // 获取所有角色列表并传递到页面
        List<Roles> roleList = roleService.getAllRoles();
        model.addAttribute("roleList", roleList);
        
        // 跳转到编辑页面
        return "user/edit";
    }

    /**
     * 新增/修改用户信息
     * @param user 用户信息
     * @return
     */
    @AdminToken
    @PostMapping("/update")
    public String updateUser(SysUser user, Model model){
        try {
            // 设置默认值
            if(user.getUserId() == null || user.getUserId().isEmpty()){
                // 新增用户
                user.setUserId(UUID.randomUUID().toString());
                user.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                user.setStatus((byte) 1); // 默认状态为可用
                
                // 设置默认头像
                if(user.getImgUrl() == null || user.getImgUrl().isEmpty()){
                    user.setImgUrl("/images/default-avatar.png");
                }
                
                // 设置初始密码并加密
                user.setPassword(MD5.md5("123456"));
                
                // 新增用户
                userService.insert(user);
                logger.info("新增用户ID: {}", user.getUserId());
            } else {
                // 更新用户信息
                userService.updateByPrimaryKeySelective(user);
                logger.info("更新用户ID: {}", user.getUserId());
            }
            
            return "redirect:/user/list";
        } catch (Exception e) {
            model.addAttribute("errorMsg", "用户更新失败: " + e.getMessage());
            return "user/list";
        }
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return
     */
    @AdminToken
    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        userService.deleteByPrimaryKey(userId);
        logger.info("删除用户ID: {}", userId);
        return "redirect:/user/list";
    }

    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 状态值
     * @return
     */
    @AdminToken
    @GetMapping("/updateStatus")
    public String updateStatus(@RequestParam("id") String id, @RequestParam("status") Integer status){
        userService.updateStatusByPrimaryKey(id,status);
        logger.info("更新用户ID: {} 状态为: {}", id, status);
        return "redirect:/user/list";
    }

}