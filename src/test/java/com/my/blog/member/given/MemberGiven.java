package com.my.blog.member.given;

import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.Password;

public class MemberGiven {

    public static CreateRequest getMemberRequest(){
        return CreateRequest.builder()
                .email(Email.from("test@google.com"))
                .password(Password.from("nono"))
                .name(Name.from("이승은"))
                .build();
    }
}
