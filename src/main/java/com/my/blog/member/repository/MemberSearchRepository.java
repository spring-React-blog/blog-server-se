package com.my.blog.member.repository;

import com.my.blog.global.security.dto.LoginAuth;
import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.MemberSchCondition;
import com.my.blog.member.entity.vo.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberSearchRepository {
    Optional<LoginAuth> findByLoginEmail(Email email);
    Page<MemberResponse> search(MemberSchCondition condition, Pageable pageable);
}
