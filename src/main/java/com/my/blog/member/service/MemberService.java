package com.my.blog.member.service;

import com.my.blog.common.errorcode.MemberErrorCode;
import com.my.blog.common.exception.CommonException;
import com.my.blog.member.entity.Member;
import com.my.blog.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MemberService {
    private MemberRepository memberRepository;

    public Long save(Member member){
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public Member findById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));
    }

}
