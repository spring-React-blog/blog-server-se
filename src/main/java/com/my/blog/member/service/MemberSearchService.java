package com.my.blog.member.service;

import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.MemberSchCondition;
import com.my.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberSearchService {
    private final MemberRepository memberSearchRepository;

    public Page<MemberResponse> getMembers(final MemberSchCondition condition, final Pageable pageable){
        return memberSearchRepository.search(condition,pageable);
    }
}
