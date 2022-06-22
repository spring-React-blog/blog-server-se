package com.my.blog.member.dto;

import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.NickName;
import com.my.blog.member.entity.vo.RoleType;
import lombok.Data;

@Data
public class MemberSchCondition {

    private Long id;
    private Name name;
    private NickName nickName;
    private RoleType roleType;
}
