package com.my.blog.member.entity.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NickName {

    @NotBlank
    @Size(min=3,max=8,message = "3~8자 내로 입력해주세요.")
    private String nickname;

    public static NickName from(String nickname){
        return new NickName(nickname);
    }

}
