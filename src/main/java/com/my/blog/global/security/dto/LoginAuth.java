package com.my.blog.global.security.dto;

import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.entity.vo.RoleType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginAuth {
    private Email email;
    private Password password;
    private RoleType roleType;
}
