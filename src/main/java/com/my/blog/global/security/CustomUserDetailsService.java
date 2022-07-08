package com.my.blog.global.security;

import com.my.blog.global.common.exception.CommonException;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.error.MemberErrorCode;
import com.my.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  memberRepository.findByLoginEmail(Email.from(email))
                .map(CustomUserDetails::from)
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));
    }
}
