package com.my.blog.member.vo;

import com.my.blog.category.entity.Category;
import com.my.blog.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberRequest {

    private Email email;
    private Password password;
    private String name;
    private String nickName;

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .nickname(this.nickName)
                .build();
    }

}
