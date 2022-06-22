package com.my.blog.member.repository;

import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.MemberSchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberSearchRepository {

    Page<MemberResponse> search(MemberSchCondition condition, Pageable pageable);
}
