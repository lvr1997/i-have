package com.lvr.ihave.web.config;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //获取请求中的参数
        String language = request.getParameter("l");

        //获取默认的Locale
        Locale locale = Locale.getDefault();

        //解析获取到的参数
        if (StringUtils.isNotEmpty(language)) { //如果language不为空
            //开始解析 zh_CN
            String[] s = language.split("_");
            //如果获取到参数就使用获取到的参数
            return Locale.of(s[0],s[1]);
        }
        //如果没有获取到参数就是用默认的参数
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}