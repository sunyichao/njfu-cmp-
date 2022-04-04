package com.example.springboothello_world.Interceptor;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yicha
 * @date 2022/4/4
 * @time 0:54
 */
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //"/user/file/avatar/**"请求的路径，"\\CoolCatFile\\Avatar\\"映射的本地路径
        registry.addResourceHandler("/upload");
                //.addResourceLocations("file:D:" + "\\CoolCatFile\\Avatar\\");
    }
}
