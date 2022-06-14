package com.my.blog.security;

import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.entity.vo.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails , Serializable {
    private final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Long id;
    private Email email;
    private Password password;
    private RoleType role;

    public CustomUserDetails(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.role = member.getRoleType();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role));
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password.password();
    }

    @Override
    public String getUsername() {
        return email.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
