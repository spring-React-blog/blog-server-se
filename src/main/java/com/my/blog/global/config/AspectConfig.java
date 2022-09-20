package com.my.blog.global.config;

import com.my.blog.global.common.aspect.LogAspect;
import com.my.blog.global.common.aspect.RetryAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {
    @Bean
    public LogAspect logAspect(){
        return new LogAspect();
    }
    @Bean
    public RetryAspect retryAspect(){
        return new RetryAspect();
    }
}
