package com.my.blog.member.entity.vo;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Password {

    private PasswordEncoder passwordEncoder;

    @Size(min=8,max = 12,message = "8~12자 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]$"
            , message = "영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야합니다.")
    private String password;

    public Password(String password) {
        this.password = password;
    }

    public String password(){
        return password;
    }
    public String encode(){
        return passwordEncoder.encode(this.password);
    }

    public static Password from(String password){
        return new Password(password);
    }
}
