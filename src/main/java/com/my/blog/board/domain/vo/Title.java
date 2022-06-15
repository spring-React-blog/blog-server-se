package com.my.blog.board.domain.vo;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class Title {

    @NotBlank
    @Size(max=30,message = "30자 이하로 입력해주세요.")
    private String title;

    public static Title of(String title){
        return new Title(title);
    }

}
