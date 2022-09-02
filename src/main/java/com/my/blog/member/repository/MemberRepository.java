package com.my.blog.member.repository;

import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>, MemberSearchRepository {
    Optional<Member> findByEmail(Email email);

}
