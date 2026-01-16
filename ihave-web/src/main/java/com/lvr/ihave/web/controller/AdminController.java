package com.lvr.ihave.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvr.ihave.constant.Constant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * 后台管理员登录认证接口
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * 登录验证
     * @param username
     * @param password
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session){
        //检查用户名和密码是否正确
        if("admin".equals(username) && "123456".equals(password)){
            //当用户名为admin且密码是123456的时候可以进入主页面
            session.setAttribute(Constant.LOGIN_USER_KEY, username);
            return "redirect:/main.html";
        }else{
            //用户名或者密码错误的时候给出相应的错误提示,并且返回主页面
            model.addAttribute("msg","用户名或者密码输入错误");
            return "login";
        }
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        
        session.invalidate();
        return "redirect:/login.html";
    }

}
