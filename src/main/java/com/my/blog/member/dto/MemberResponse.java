package com.my.blog.member.dto;

import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.NickName;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberResponse {
    private Long id;
    private Email email;
    private Name name;
    private NickName nickName;

    @Builder
    public MemberResponse(
            Long id,
            Email email,
            Name name,
            NickName nickName
    ){
        this.id=id;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
    }


}
