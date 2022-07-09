package com.my.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.context.WebApplicationContext;

@TestConfiguration
public class MockMvcConfig {
    @Autowired
    private WebApplicationContext context;
}
