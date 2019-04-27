package com.lrk.xk.xk.config;

import com.lrk.xk.xk.interceptor.AccessInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author lrk
 * @date 2019/4/27下午4:37
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private AccessInterceptor accessInterceptor;

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> xkHiddenHttpMethodFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HiddenHttpMethodFilter());
        registration.addUrlPatterns(new String[] {"/*"});
        registration.setName("ftlRequestAttributeFilter");
        return registration;
    }

    @Bean
    public FilterRegistrationBean<OpenSessionInViewFilter> xkOpenSessionInViewFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new OpenSessionInViewFilter());
        registration.addUrlPatterns(new String[] {"/*"});
        registration.setName("ftlRequestAttributeFilter");
        return registration;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").
            addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(accessInterceptor).addPathPatterns("/**")
            .excludePathPatterns("/static/**").excludePathPatterns("/templates/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/xk").setViewName("forward:index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        WebMvcConfigurer.super.addViewControllers(registry);
    }
}
