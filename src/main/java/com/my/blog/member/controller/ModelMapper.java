package com.my.blog.member.controller;

import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.dto.request.UpdateRequest;
import com.my.blog.member.service.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class ModelMapper {

    public MemberDTO createMember(CreateRequest request){
        return MemberDTO.builder()
                .nickname(request.getNickName())
                .password(request.getPassword())
                .name(request.getName())
                .build();
    }

    public MemberDTO updateMember(UpdateRequest request){
        return MemberDTO.builder()
                .id(request.getId())
                .nickname(request.getNickName())
                .password(request.getPassword())
                .name(request.getName())
                .build();
    }

    public MemberResponse getResponse(MemberDTO member){
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickName(member.getNickname())
                .build();
    }

}
