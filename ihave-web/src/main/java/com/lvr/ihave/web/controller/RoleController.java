package com.lvr.ihave.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvr.ihave.business.service.RoleService;
import com.lvr.ihave.pojo.Roles;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Roles> roleList = roleService.getAllRoles();
        model.addAttribute("roleList", roleList);
        return "role/list";
    }

    /**
     * 进入编辑角色页面
     * 
     * @param id    角色ID
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id", required = false) String id, Model model) {
        if (id == null || id.isEmpty()) {
            Roles role = new Roles();
            model.addAttribute("role", role);
        } else {
            Roles role = roleService.getRoleById(id);
            model.addAttribute("role", role);
        }

        return "role/edit";
    }

    /**
     * 提交编辑角色信息
     * 
     * @param role 角色对象
     * @return
     */
    @PostMapping("/edit")
    public String editSubmit(Roles role) {
        // 新增角色
        if (role.getId() == null) {
            roleService.addRole(role);
        }
        // 修改角色
        roleService.updateRole(role);
        return "redirect:/role/list";
    }

    /**
     * 删除角色
     * 
     * @param id 角色ID
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        roleService.deleteRole(id);
        return "redirect:/role/list";
    }
}
