package com.my.blog.member.service;

import com.my.blog.common.errorcode.MemberErrorCode;
import com.my.blog.common.exception.CommonException;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.dto.MemberDTO;
import com.my.blog.member.repository.MemberRepository;
import com.my.blog.member.dto.ModelMapper;
import com.my.blog.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO){
        Member member = ModelMapper.createMember(memberDTO);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public MemberResponse update(MemberDTO memberDTO){
        Member member = ModelMapper.updateMember(memberDTO);
        Member findMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));

        Member updated = findMember.updateInfo(member);
        return MemberResponse.of(updated);
    }

    public MemberResponse findById(Long id){
        return memberRepository.findById(id).map(MemberResponse::of)
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND))
                ;
    }



}
