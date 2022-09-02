package com.my.blog.global.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "embedded", name = "redis", havingValue = "true")
public class EmbededRedisConfig {

    private RedisServer redisServer;

    @Value("${spring.redis.port}")
    private int port;

    @PostConstruct
    public void redisServer()  {
        redisServer = RedisServer.builder()
                .port(port)
                .setting("maxmemory 128M")
                .build();
        try {
            redisServer.start();
        } catch(Exception ex) {
            log.info("redis server start error {}" , ex);
        }

    }

    @PreDestroy
    public void stopRedis(){
        if(redisServer!=null){
            redisServer.stop();
        }
    }


}
