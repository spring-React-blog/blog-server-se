package com.my.blog.support.config;

import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.RoleType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface AuthUser {
    String username();
    String password();
    RoleType role();
}
