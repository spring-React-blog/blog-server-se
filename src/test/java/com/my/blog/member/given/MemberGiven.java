package com.my.blog.member.given;

import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.NickName;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.service.dto.MemberDTO;

public class MemberGiven {

    public static CreateRequest getMemberRequest(){
        return CreateRequest.builder()
                .email(Email.from("seung90@gmail.com"))
                .password(Password.from("q1w2e3r4"))
                .name(Name.from("이승은"))
                .build();
    }

    public static MemberDTO getMemberDTO(){
        return MemberDTO.builder()
                .id(1L)
                .email(Email.from("seung90@gmail.com"))
                .password(Password.from("q1w2e3r4"))
                .name(Name.from("s"))
                .build();
    }
    public static Member getMemberEntity(){
        return Member.builder()
                .id(1L)
                .email(Email.from("seung90@gmail.com"))
                .password(Password.from("q1w2e3r4"))
                .name(Name.from("seung"))
                .nickName(NickName.from("a"))
                .build();
    }
}
