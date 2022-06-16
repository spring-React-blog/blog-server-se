package com.my.blog.member.dto.request;

import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.NickName;
import com.my.blog.member.entity.vo.Password;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateRequest {
    private Password password;
    private Name name;
    private NickName nickName;

    @Builder
    public UpdateRequest(
            Password password,
            Name name,
            NickName nickName
    ){
        this.password = password;
        this.name = name;
        this.nickName = nickName;
    }
}
