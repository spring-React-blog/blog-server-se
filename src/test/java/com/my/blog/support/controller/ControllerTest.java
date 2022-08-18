package com.my.blog.support.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.blog.file.service.DiskUploadServiceImpl;
import com.my.blog.file.service.UploadService;
import com.my.blog.global.common.utils.FileUtil;
import com.my.blog.global.jwt.TokenProperties;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @TestConfiguration
    public class MockBeanTest {
        FileUtil fileUtil= new FileUtil();
        private final String secret = "test123test123test123test123test123test123test123test123test123";
        private final long accessTokenValidityInSeconds = 3000;
        private final long refreshTokenValidityInSeconds = 3000;
        @Bean
        public UploadService uploadService(){
            return new DiskUploadServiceImpl(fileUtil);
        }

        @Bean
        public TokenProperties tokenProperties(){
            return new TokenProperties(secret,accessTokenValidityInSeconds,refreshTokenValidityInSeconds);
        }
    }
}
