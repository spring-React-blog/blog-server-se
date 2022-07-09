package com.my.blog.member.service;

import com.my.blog.global.common.exception.CommonException;
import com.my.blog.member.controller.ModelMapper;
import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.MemberSchCondition;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.dto.request.UpdateRequest;
import com.my.blog.member.entity.Member;
import com.my.blog.member.service.dto.EntityMapper;
import com.my.blog.member.service.dto.MemberDTO;
import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.NickName;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.entity.vo.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Disabled
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberSearchService memberSearchService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ModelMapper mapper;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    EntityManager manager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    @DisplayName("회원 저장")
    public void save(){
        CreateRequest request = CreateRequest.builder()
                .password(Password.from("ddddd"))
                .name(Name.from("seungeun"))
                .nickName(NickName.from("dd"))
                .roleType(RoleType.USER)
                .build();

        MemberDTO memberDTO = mapper.createMember(request);
        Long savedId = memberService.save(memberDTO);
    }

    @Test
    @DisplayName("회원 단 건 조회")
    public void getOne(){
        MemberDTO dto = memberService.findById(1L);
        Name name = dto.getName();
        assertThat(name).isEqualTo(Name.from("seungeun"));
    }

    @Test
    @DisplayName("회원 리스트 조회")
    public void getList(){
        MemberSchCondition condition = new MemberSchCondition();
        condition.setName(Name.from("seungeun"));
        PageRequest pageable=  PageRequest.of(0,10, Sort.by("name"));
        Page<MemberResponse> members = memberSearchService.getMembers(condition, pageable);
        Long id = Long.valueOf(0);
        for(MemberResponse res : members) {
            assertThat(res.getId()).isEqualTo(Long.valueOf(id+1));
        }
    }
    @Test
    @DisplayName("회원 이름 수정")
    public void nameChanged(){
        MemberDTO dto = memberService.findById(1L);
        Member member = entityMapper.toEntity(dto);
        member.nameChange(Name.from("ssseung"));
        assertThat(member.getName()).isEqualTo("ssseung");
        assertThat(member.getNickname()).isEqualTo(NickName.from("dd"));

        MemberDTO updated = memberService.findById(1L);
        assertThat(updated.getName().getName()).isEqualTo(Name.from("ssseung").getName());

    }

    @Test
    @DisplayName("회원 수정")
    public void update() {
        UpdateRequest update = UpdateRequest.builder()
                .id(1L)
                .nickName(NickName.from("hohoho")).build();
        MemberDTO memberDTO = mapper.updateMember(update);

        MemberDTO before = memberService.findById(memberDTO.getId());
        Password beforePassword = before.getPassword();

        MemberDTO updated = memberService.update(memberDTO);
        assertThat(updated.getName()).isEqualTo(Name.from("seungeun"));
        assertThat(updated.getNickname()).isEqualTo(NickName.from("hohoho"));
        assertThat(beforePassword).isEqualTo(updated.getPassword());
    }

    @Test
    @DisplayName("비밀번호 수정")
    public void updatePwd(){
        UpdateRequest update = UpdateRequest.builder()
                .id(1L)
                .password(Password.from("q1w2e3r4")).build();
        MemberDTO memberDTO = mapper.updateMember(update);

        MemberDTO before = memberService.findById(memberDTO.getId());
        Password beforePassword = before.getPassword();

        MemberDTO updated = memberService.update(memberDTO);
        assertThat(updated.getName()).isEqualTo(Name.from("seungeun"));
        assertThat(updated.getNickname()).isEqualTo(NickName.from("hohoho"));
        assertThat(beforePassword).isEqualTo(updated.getPassword());

    }

    @Test
    @DisplayName("회원 수정")
    public void update(){
        UpdateRequest update = UpdateRequest.builder()
                .id(1L)
                .nickName(NickName.from("hohoho")).build();
        MemberDTO memberDTO = mapper.updateMember(update);

        MemberDTO before = memberService.findById(memberDTO.getId());
        Password beforePassword = before.getPassword();

        MemberDTO updated = memberService.update(memberDTO);
        assertThat(updated.getName()).isEqualTo(Name.from("seungeun"));
        assertThat(updated.getNickname()).isEqualTo(NickName.from("hohoho"));
        assertThat(beforePassword).isEqualTo(updated.getPassword());
    }
    @Test
    @DisplayName("비밀번호 수정")
    public void updatePwd(){
        UpdateRequest update = UpdateRequest.builder()
                .id(1L)
                .password(Password.from("q1w2e3r4")).build();
        MemberDTO memberDTO = mapper.updateMember(update);

        MemberDTO before = memberService.findById(memberDTO.getId());
        Password beforePassword = before.getPassword();

        MemberDTO updated = memberService.update(memberDTO);
        assertThat(updated.getNickname()).isEqualTo(NickName.from("dd"));
        assertThat(beforePassword).isNotEqualTo(updated.getPassword());
    }

    @Test
    @DisplayName("회원 삭제")
    public void delete(){
        MemberDTO dto = memberService.findById(1L);
        Member member = entityMapper.toEntity(dto);
        memberService.deleteById(member.getId());
        assertThrows( CommonException.class, ()-> memberService.findById(1L));
    }

    @Test
    @DisplayName("회원 삭제")
    public void delete(){
        MemberDTO dto = memberService.findById(1L);
        Member member = entityMapper.toEntity(dto);
        memberService.deleteById(member.getId());
        assertThrows( CommonException.class, ()-> memberService.findById(1L));
    }

}