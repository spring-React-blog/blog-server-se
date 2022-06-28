package com.my.blog.member.entity.vo;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

    @NotBlank
    @Size(min=1,max=8,message = "3~8자 내로 입력해주세요.")
    private String name;

    public static Name from(String name){
        return new Name(name);
    }

}
