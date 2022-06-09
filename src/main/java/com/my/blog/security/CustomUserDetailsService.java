package com.my.blog.security;

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
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return  memberRepository.findById(Long.valueOf(id))
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
    }
}
