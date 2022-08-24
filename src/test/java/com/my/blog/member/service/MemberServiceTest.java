package com.my.blog.member.service;

import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.MemberSchCondition;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.NickName;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.repository.MemberRepository;
import com.my.blog.member.service.dto.EntityMapper;
import com.my.blog.member.service.dto.MemberDTO;
import com.my.blog.support.service.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@UnitTest
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder encoder;

    @Mock
    EntityMapper entityMapper;

    MemberService memberService;

    MemberSearchService memberSearchService;

    @BeforeEach
    public void init(){
        this.memberService = new MemberService(memberRepository, encoder, entityMapper);
        this.memberSearchService = new MemberSearchService(memberRepository);
    }

    @Test
    @DisplayName("회원 단 건 조회")
    public void getOne(){
        //given
        Member member = Member.builder()
                .id(1L)
                .name(Name.from("seungeun"))
                .build();
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        //when
        MemberDTO dto = memberService.findById(1L);

        //then
        assertThat(dto.getName()).isEqualTo(Name.from("seungeun"));
    }

    @Test
    @DisplayName("회원 리스트 조회")
    public void getList(){
        MemberSchCondition condition = new MemberSchCondition();
        condition.setName(Name.from("seungeun"));
        PageRequest pageable=  PageRequest.of(0,10, Sort.by("name"));

        //given
        List<MemberResponse> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MemberResponse response = MemberResponse.builder()
                    .id(Long.valueOf(i))
                    .email(Email.from("seung" + i + "@gmail.com"))
                    .name(Name.from("seungeun"))
                    .nickName(NickName.from("ss"))
                    .build();
            list.add(response);
        }

        Page<MemberResponse> memberResponses = new PageImpl<>(list);
        given(memberRepository.search(condition,pageable)).willReturn(memberResponses);

        //when
        Page<MemberResponse> members = memberSearchService.getMembers(condition, pageable);

        //then
        List<MemberResponse> content = members.getContent();

        assertThat(content.size()).isEqualTo(5);
        assertThat( members.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("회원 생성")
    public void create(){
        MemberDTO dto = MemberDTO.builder()
                .email(Email.from("seung90@gmail.com"))
                .password(Password.from("q1w2e3r4"))
                .name(Name.from("seung"))
                .build();
        Long savedId = memberService.save(dto);
        assertThat(savedId).isEqualTo(1L);


    }


}