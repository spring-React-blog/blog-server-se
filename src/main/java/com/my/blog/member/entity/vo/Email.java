package com.my.blog.member.entity.vo;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class Email {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @javax.validation.constraints.Email
    private String email;

    public static Email from(String email){
        return new Email(email);
    }
}
