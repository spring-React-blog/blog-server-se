package com.my.blog.member.entity.dto;

import com.my.blog.member.entity.vo.Birth;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.NickName;
import com.my.blog.member.entity.vo.Password;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class MemberDTO {
    private Email email;
    private Password password;
    private String name;
    private NickName nickname;
    private Birth birth;
}
