package com.my.blog.member.service.dto;

import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.RoleType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class EntityMapper {
    public static Member toEntity(MemberDTO dto){
        return Member.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .nickName(dto.getNickname())
                .roleType(RoleType.USER)
                .build();
    }

    public static MemberDTO toDTO(Member member){
        return MemberDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .nickname(member.getNickname())
                .build();
    }
}
