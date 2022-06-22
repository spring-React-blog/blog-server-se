package com.my.blog.member.service.dto;

import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.*;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class MemberDTO {
    private Long id;
    private Email email;
    private Password password;
    private Name name;
    private NickName nickname;
    private Birth birth;

}
