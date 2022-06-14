package com.my.blog.member.dto;

import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.dto.MemberDTO;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.dto.request.UpdateRequest;

public class ModelMapper {

    public static MemberDTO createMember(CreateRequest request){
        MemberDTO dto = new MemberDTO();
        dto.setName(request.getName());
        dto.setNickname(request.getNickName());
        dto.setPassword(request.getPassword());
        return dto;
    }

    public static MemberDTO updateMember(UpdateRequest request){
        MemberDTO dto = new MemberDTO();
        dto.setName(request.getName());
        dto.setNickname(request.getNickName());
        dto.setPassword(request.getPassword());
        return dto;
    }

    public static Member createMember(MemberDTO dto){
        return Member.builder()
                .nickName(dto.getNickname())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }


    public static Member updateMember(MemberDTO dto){
        return Member.builder()
                .nickName(dto.getNickname())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
    }


}
