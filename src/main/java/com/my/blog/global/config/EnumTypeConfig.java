package com.my.blog.global.config;

import com.my.blog.board.domain.vo.Status;
import com.my.blog.global.common.mapper.EnumMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumTypeConfig {
    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();

        enumMapper.put("BoardStatus", Status.class);

        return enumMapper;
    }
}
