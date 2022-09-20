package com.my.blog.global.config;

import com.my.blog.file.service.DiskUploadServiceImpl;
import com.my.blog.file.service.UploadService;
import com.my.blog.global.common.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UploadConfig {
    private final FileUtil fileUtil;

    @Bean
    @ConditionalOnProperty(prefix = "upload", name = "service", havingValue = "local")
    public UploadService uploadService(){
        return new DiskUploadServiceImpl(fileUtil);
    }


}
