package com.my.blog.member.service;

import com.my.blog.global.common.exception.CommonException;
import com.my.blog.member.entity.Member;
import com.my.blog.member.service.dto.EntityMapper;
import com.my.blog.member.service.dto.MemberDTO;
import com.my.blog.member.error.MemberErrorCode;
import com.my.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper mapper;
    public Long save(final MemberDTO memberDTO){
        Member member = mapper.toEntity(memberDTO);
        member.encode(member.getPassword(),passwordEncoder);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public MemberDTO update(final MemberDTO memberDTO){
        Member member = mapper.toEntity(memberDTO);//.encode(passwordEncoder);

        Member findMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));

        Member updated = findMember.updateInfo(member,passwordEncoder);

        return mapper.toDTO(updated);
    }

    public Long deleteById(final Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));
        Member deleted = member.delete();
        return deleted.getId();
    }


    @Transactional(readOnly = true)
    public MemberDTO findById(final Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));
        if(member.hasDeleted()) throw new CommonException(MemberErrorCode.USER_HAS_DELETED);
        return mapper.toDTO(member);
    }

}
