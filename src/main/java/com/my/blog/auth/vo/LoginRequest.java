package com.my.blog.auth.vo;

import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.entity.vo.RoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LoginRequest {

    private Email email;
    private Password password;

}
