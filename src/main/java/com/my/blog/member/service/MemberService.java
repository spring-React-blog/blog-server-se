package com.my.blog.member.service;

import com.my.blog.member.error.MemberErrorCode;
import com.my.blog.global.common.exception.CommonException;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.dto.MemberDTO;
import com.my.blog.member.repository.MemberRepository;
import com.my.blog.member.dto.ModelMapper;
import com.my.blog.member.dto.MemberResponse;
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

    @Transactional
    public Long save(MemberDTO memberDTO){
        Member member = ModelMapper.createMember(memberDTO).encode(passwordEncoder);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }
    @Transactional
    public MemberResponse update(MemberDTO memberDTO){
        Member member = ModelMapper.updateMember(memberDTO).encode(passwordEncoder);

        Member findMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));

        Member updated = findMember.updateInfo(member);
        return ModelMapper.getResponse(updated);
    }

    @Transactional
    public void deleteById(Long id){
        memberRepository.deleteById(id);
    }

    /*public MemberResponse findById(Long id){
        return memberRepository.findById(id).map(ModelMapper::getResponse)
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND))
                ;
    }*/
    public Member findById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND))
                ;
    }

}
