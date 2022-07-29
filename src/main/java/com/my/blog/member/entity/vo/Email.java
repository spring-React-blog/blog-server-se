package com.my.blog.member.entity.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@Embeddable
public class Email {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @javax.validation.constraints.Email
    @Column(name="email")
    private String email;

    @JsonValue
    public String email() {
        return email;
    }

    public static Email from(String email) {
        return new Email(email);
    }
}
