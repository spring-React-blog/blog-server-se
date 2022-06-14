package com.my.blog.member.service;

import com.my.blog.member.entity.dto.MemberDTO;
import com.my.blog.member.entity.vo.NickName;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.entity.vo.RoleType;
import com.my.blog.member.dto.ModelMapper;
import com.my.blog.member.dto.request.CreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder encoder;

    @Test
    @DisplayName("pwd 에러가 난 member save")
    public void save(){
        CreateRequest request = CreateRequest.builder()
                .password(Password.from("ddddd"))
                .name("seungeun")
                .nickName(NickName.from("dd"))
                .roleType(RoleType.USER)
                .build();

        MemberDTO member = ModelMapper.createMember(request);
        Long savedId = memberService.save(member);

        assertThat(savedId).isEqualTo(1L);
        //MemberResponse response = memberService.findById(savedId);
    }

    @Test
    @DisplayName("회원 목록")
    public void list(){

    }


}