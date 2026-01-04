package com.lvr.ihave.web.interceptor;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.lvr.ihave.annotation.AdminToken;
import com.lvr.ihave.annotation.PassToken;
import com.lvr.ihave.annotation.UserLoginToken;
import com.lvr.ihave.business.service.UserService;
import com.lvr.ihave.constant.Constant;
import com.lvr.ihave.constant.StatusEnum;
import com.lvr.ihave.pojo.SysUser;
import com.lvr.ihave.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    @Lazy
    UserService userService;

    /**
     * 登录拦截方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        
        // 检查是否有PassToken注解，有则跳过认证
        if (handlerMethod.getMethod().isAnnotationPresent(PassToken.class) || 
            handlerMethod.getBeanType().isAnnotationPresent(PassToken.class)) {
            return true;
        }

        // 检查是否有AdminToken注解
        boolean hasAdminToken = handlerMethod.getMethod().isAnnotationPresent(AdminToken.class) || 
                              handlerMethod.getBeanType().isAnnotationPresent(AdminToken.class);
        
        // 检查是否有UserLoginToken注解
        boolean hasUserLoginToken = handlerMethod.getMethod().isAnnotationPresent(UserLoginToken.class) || 
                                   handlerMethod.getBeanType().isAnnotationPresent(UserLoginToken.class);

        // 如果没有任何认证注解，默认通过
        if (!hasAdminToken && !hasUserLoginToken) {
            return true;
        }

        // AdminToken认证 - 校验服务端Session（后台管理端）
        if (hasAdminToken) {
            return validateAdminToken(request, response);
        }
        
        // UserLoginToken认证 - 校验客户端token（前台客户端）
        if (hasUserLoginToken) {
            return validateUserLoginToken(request, response);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理线程本地变量
        UserContext.removeCurrentId();
    }

    /**
     * 验证管理员Token - 校验服务端Session（后台管理端）
     */
    private boolean validateAdminToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 从Session中获取管理员登录信息
        String username = (String) request.getSession().getAttribute(Constant.LOGIN_USER_KEY);
        
        if (StringUtils.isEmpty(username)) {
            log.error("管理员未登录，请先登录");
            // 后台管理端未登录时重定向到登录页面
            response.sendRedirect("/login.html");
            return false;
        }

        // 验证是否为管理员用户
        if (!"admin".equals(username)) {
            log.error("非管理员用户访问后台管理页面");
            response.sendRedirect("/login.html");
            return false;
        }

        log.info("管理员登录校验成功，用户名: {}", username);
        return true;
    }

    /**
     * 验证用户登录Token - 校验客户端token（前台客户端）
     */
    private boolean validateUserLoginToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 从请求头中获取token
        String token = request.getHeader(Constant.TOKEN);
        
        if (StringUtils.isEmpty(token)) {
            log.error("用户未登录，请先登录");
            // 前台客户端未登录时返回JSON格式错误信息
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"用户未登录，请先登录\",\"data\":null}");
            return false;
        }

        try {
            // 验证JWT token
            boolean result = JWTUtil.verify(token, Constant.LOGIN_USER_KEY.getBytes());
            if (!result) {
                log.error("用户token验证失败！token is {}, uri is {}", token, request.getRequestURI());
                // 前台客户端token验证失败时返回JSON格式错误信息
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"msg\":\"token验证失败\",\"data\":null}");
                return false;
            }

            // 解析token获取用户信息
            JWT jwt = JWTUtil.parseToken(token);
            String userId = (String) jwt.getPayload("userId");

            if (StringUtils.isEmpty(userId)) {
                log.error("用户token解析失败");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"msg\":\"token解析失败\",\"data\":null}");
                return false;
            }

            // 验证用户是否存在
            SysUser user = userService.selectByPrimaryKey(userId);
            if (user == null) {
                log.error("用户不存在");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"msg\":\"用户不存在\",\"data\":null}");
                return false;
            }

            // 设置当前用户上下文
            UserContext.setCurrentId(userId);
            log.info("用户token校验成功，用户ID: {}", userId);
            return true;
        } catch (Exception e) {
            log.error("用户token验证异常: {}", e.getMessage());
            // 捕获JWT解析异常，返回友好的错误信息
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"token格式错误\",\"data\":null}");
            return false;
        }
    }
}