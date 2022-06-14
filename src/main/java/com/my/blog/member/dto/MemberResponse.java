package com.my.blog.member.dto;

import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.NickName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberResponse {
    private Long id;
    private Email email;
    private String name;
    private NickName nickName;

    @Builder
    public MemberResponse(
            Long id,
            Email email,
            String name,
            NickName nickName
    ){
        this.id=id;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
    }

    public static MemberResponse of(Member member){
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickName(member.getNickname())
                .build();
    }
}
