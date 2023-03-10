package com.example.graduation_project.Config;


import com.example.graduation_project.Intercptor.Loginintercptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/*
* 继承webmvcConfigurer开启定制化配置
* */
/*
* 编写拦截器步骤
* 1、编写一个拦截器实现HandlerIntercptor接口
* 2、将拦截器注册到容器中（实现webconfigurer的addintercptor方法·）
* 3、指定拦截规则
* */
@Configuration
public class AdminCongfig implements WebMvcConfigurer {

    /*
    * 开启拦截器/**会默认把所有请求都拦截，包括静态资源（css,js,images）
    *所以要放行静态资源
    */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Loginintercptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login","/druid/","/assets/**","/pages-register.html","/PatientRegister");
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("E:/Data/");
        return factory.createMultipartConfig();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:E:\\Data\\file\\");

    }

    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter(){
        return new OrderedHiddenHttpMethodFilter();
    }
}
