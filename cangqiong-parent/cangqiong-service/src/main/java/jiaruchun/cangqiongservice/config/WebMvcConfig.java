package jiaruchun.cangqiongservice.config;

import jiaruchun.cangqiongservice.interceptor.JwtTokenAdminInterceptor;
import jiaruchun.cangqiongservice.interceptor.JwtTokenUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*拦截器等MVC组件配置*/
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 JwtTokenAdminInterceptor 拦截器
       log.info("...注册 JwtTokenAdminInterceptor 拦截器");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**") // 拦截所有请求
                .excludePathPatterns("/admin/employee/login"); // 排除登录请求

        log.info("...注册 JwtTokenUserInterceptor 拦截器");
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**") // 拦截所有请求
                .excludePathPatterns("/user/user/login")//排除登录请求
                .excludePathPatterns("/user/shop/status");// 排除shop请求
    }
}