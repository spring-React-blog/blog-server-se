package com.my.blog.global.security;

import com.my.blog.global.security.dto.LoginAuth;
import com.my.blog.member.entity.vo.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class CustomUserDetails extends User  {
    private final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    public static final String ROLE = "ROLE_";

    private CustomUserDetails(final LoginAuth loginAuth) {
        super(loginAuth.getEmail().getEmail(), loginAuth.getPassword().password(), getAuthorities(loginAuth.getRoleType()));
    }
    public static CustomUserDetails from(final LoginAuth loginAuth){
        return new CustomUserDetails(loginAuth);
    }

    public static List<GrantedAuthority> getAuthorities(RoleType role) {
        return List.of(getAuthority(role));
    }

    public static List<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(CustomUserDetails::getAuthority)
                .collect(toList());
    }

    private static SimpleGrantedAuthority getAuthority(RoleType role){
        return new SimpleGrantedAuthority(ROLE + role);
    }

    private static SimpleGrantedAuthority getAuthority(String role){
        return new SimpleGrantedAuthority(ROLE + role);
    }

}
