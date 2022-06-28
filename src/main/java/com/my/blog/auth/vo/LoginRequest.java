package com.my.blog.auth.vo;

import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Password;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    private Email email;
    private Password password;

}
