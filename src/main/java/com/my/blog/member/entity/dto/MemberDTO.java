package com.my.blog.member.entity.dto;

import com.my.blog.member.entity.vo.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class MemberDTO {
    private Email email;
    private Password password;
    private Name name;
    private NickName nickname;
    private Birth birth;
}
