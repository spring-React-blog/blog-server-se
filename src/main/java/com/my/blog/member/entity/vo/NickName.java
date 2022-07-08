package com.my.blog.member.entity.vo;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NickName {

    @NotBlank
    @Size(min=3,max=8,message = "3~8자 내로 입력해주세요.")
    @Column(name="nickname")
    private String nickname;

    public static NickName from(String nickname){
        return new NickName(nickname);
    }

}
