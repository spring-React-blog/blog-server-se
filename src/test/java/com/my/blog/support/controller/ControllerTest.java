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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
public class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("jwt.secret", () -> "seungeung112220202094885213nfnfkdfm23023jdflsfseungeung112220202094885213nfnfkdfm23023jdflsf");
        registry.add("jwt.accessTokenValidityInSeconds", () -> Long.valueOf(3000));
        registry.add("jwt.refreshTokenValidityInSeconds", () -> Long.valueOf(3000));

        registry.add("upload.service", () -> "local");
        registry.add("file.upload.path", () -> "/images");
    }


}
