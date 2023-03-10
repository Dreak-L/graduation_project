package com.example.graduation_project.Intercptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
* 登录拦截器
* 1、配置好拦截器要拦截哪些请求
* 2、把这些配置放在容器中
*
* */
public class Loginintercptor implements HandlerInterceptor {
    /*
    * 在目标方法执行之前
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loginuser = session.getAttribute("loginuser");
        Object loginpatient = session.getAttribute("loginpatient");
        if (loginuser != null || loginpatient !=null){
            //放行
            return true;
        }
        //拦截
        request.setAttribute("msg","请先登录！");
        //重定向到项目根路径
        request.getRequestDispatcher("/").forward(request,response);
        return false;
    }

    /*
    * 目标方法执行之后，页面渲染之前
    * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    /*
    * 页面渲染之后
    * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}