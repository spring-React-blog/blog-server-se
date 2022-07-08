package com.my.blog.member.entity.vo;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

    @Size(min=8,max = 12,message = "8~12자 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]$"
            , message = "영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야합니다.")
    @NotBlank
    @Column(name="password")
    private String password;

    public String password(){
        return this.password;
    }
    public Password encode(PasswordEncoder passwordEncoder){
        return from(passwordEncoder.encode(this.password));
    }

    public boolean matches(Password target, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(this.password, target.getPassword());
    }
    public static Password from(String password){
        return new Password(password);
    }
}
