package com.my.blog.member.dto.request;

import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.NickName;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.entity.vo.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateRequest {

    private Email email;
    private Password password;
    private Name name;
    private NickName nickName;

    private RoleType roleType;

    @Builder
    public CreateRequest(
            Email email,
            Password password,
            Name name,
            NickName nickName,
            RoleType roleType
    ){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.roleType = roleType;
    }
    /*
    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .nickName(this.nickName)
                .roleType(this.roleType)
                .build();
    }*/

}
