package com.my.blog.support.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.blog.file.service.DiskUploadServiceImpl;
import com.my.blog.file.service.UploadService;
import com.my.blog.global.common.utils.FileUtil;
import com.my.blog.global.jwt.TokenProperties;
import com.my.blog.global.jwt.TokenProvider;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
public class ControllerTest implements BeforeEachCallback {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    TokenProvider tokenProvider;
    TokenProperties tokenProperties;
    //requires a fresh instance for each @Test method
    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        tokenProperties = new TokenProperties(
                "qwertyasdfghzxcvb234567qwertsdfghxcvb3456sdfghxcvbqwertyasdfghzxcvb234567qwertsdfghxcvb3456sdfghxcvb",
                Long.valueOf(3000),
                Long.valueOf(3000)
        );
        tokenProvider = new TokenProvider(tokenProperties);
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("aws.client", () -> "false");
        registry.add("cloud.aws.region.static", () -> "ap-northeast-2");
        registry.add("upload.service", () -> "local");
        registry.add("cloud.aws.stack.auto",() -> "false");
        registry.add("file.upload.path", () -> "/images");
    }


}
